import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { ITaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from 'app/entities/task-card/task-card.service';

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html',
})
export class CommentUpdateComponent implements OnInit {
  isSaving = false;
  taskcards: ITaskCard[] = [];

  editForm = this.fb.group({
    id: [],
    content: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    taskCardId: [],
  });

  constructor(
    protected commentService: CommentService,
    protected taskCardService: TaskCardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      if (!comment.id) {
        const today = moment().startOf('day');
        comment.creationDate = today;
        comment.modificationDate = today;
      }

      this.updateForm(comment);

      this.taskCardService.query().subscribe((res: HttpResponse<ITaskCard[]>) => (this.taskcards = res.body || []));
    });
  }

  updateForm(comment: IComment): void {
    this.editForm.patchValue({
      id: comment.id,
      content: comment.content,
      creationDate: comment.creationDate ? comment.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: comment.modificationDate ? comment.modificationDate.format(DATE_TIME_FORMAT) : null,
      taskCardId: comment.taskCardId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    return {
      ...new Comment(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      taskCardId: this.editForm.get(['taskCardId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITaskCard): any {
    return item.id;
  }
}
