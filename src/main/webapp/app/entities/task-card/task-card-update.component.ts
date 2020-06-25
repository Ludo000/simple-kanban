import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaskCard, TaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from './task-card.service';
import { IKanbanColumn } from 'app/shared/model/kanban-column.model';
import { KanbanColumnService } from 'app/entities/kanban-column/kanban-column.service';

@Component({
  selector: 'jhi-task-card-update',
  templateUrl: './task-card-update.component.html',
})
export class TaskCardUpdateComponent implements OnInit {
  isSaving = false;
  kanbancolumns: IKanbanColumn[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    colorHexCode: [],
    creationDate: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    limitDate: [],
    kanbanColumnId: [],
  });

  constructor(
    protected taskCardService: TaskCardService,
    protected kanbanColumnService: KanbanColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCard }) => {
      if (!taskCard.id) {
        const today = moment().startOf('day');
        taskCard.creationDate = today;
        taskCard.modificationDate = today;
        taskCard.limitDate = today;
      }

      this.updateForm(taskCard);

      this.kanbanColumnService.query().subscribe((res: HttpResponse<IKanbanColumn[]>) => (this.kanbancolumns = res.body || []));
    });
  }

  updateForm(taskCard: ITaskCard): void {
    this.editForm.patchValue({
      id: taskCard.id,
      name: taskCard.name,
      description: taskCard.description,
      colorHexCode: taskCard.colorHexCode,
      creationDate: taskCard.creationDate ? taskCard.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: taskCard.modificationDate ? taskCard.modificationDate.format(DATE_TIME_FORMAT) : null,
      limitDate: taskCard.limitDate ? taskCard.limitDate.format(DATE_TIME_FORMAT) : null,
      kanbanColumnId: taskCard.kanbanColumnId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskCard = this.createFromForm();
    if (taskCard.id !== undefined) {
      this.subscribeToSaveResponse(this.taskCardService.update(taskCard));
    } else {
      this.subscribeToSaveResponse(this.taskCardService.create(taskCard));
    }
  }

  private createFromForm(): ITaskCard {
    return {
      ...new TaskCard(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      colorHexCode: this.editForm.get(['colorHexCode'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      limitDate: this.editForm.get(['limitDate'])!.value ? moment(this.editForm.get(['limitDate'])!.value, DATE_TIME_FORMAT) : undefined,
      kanbanColumnId: this.editForm.get(['kanbanColumnId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskCard>>): void {
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

  trackById(index: number, item: IKanbanColumn): any {
    return item.id;
  }
}
