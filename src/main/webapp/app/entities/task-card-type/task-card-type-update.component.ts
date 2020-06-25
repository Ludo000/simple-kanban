import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaskCardType, TaskCardType } from 'app/shared/model/task-card-type.model';
import { TaskCardTypeService } from './task-card-type.service';
import { ITaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from 'app/entities/task-card/task-card.service';

@Component({
  selector: 'jhi-task-card-type-update',
  templateUrl: './task-card-type-update.component.html',
})
export class TaskCardTypeUpdateComponent implements OnInit {
  isSaving = false;
  taskcards: ITaskCard[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    taskCardId: [],
  });

  constructor(
    protected taskCardTypeService: TaskCardTypeService,
    protected taskCardService: TaskCardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardType }) => {
      this.updateForm(taskCardType);

      this.taskCardService.query().subscribe((res: HttpResponse<ITaskCard[]>) => (this.taskcards = res.body || []));
    });
  }

  updateForm(taskCardType: ITaskCardType): void {
    this.editForm.patchValue({
      id: taskCardType.id,
      name: taskCardType.name,
      taskCardId: taskCardType.taskCardId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskCardType = this.createFromForm();
    if (taskCardType.id !== undefined) {
      this.subscribeToSaveResponse(this.taskCardTypeService.update(taskCardType));
    } else {
      this.subscribeToSaveResponse(this.taskCardTypeService.create(taskCardType));
    }
  }

  private createFromForm(): ITaskCardType {
    return {
      ...new TaskCardType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      taskCardId: this.editForm.get(['taskCardId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskCardType>>): void {
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
