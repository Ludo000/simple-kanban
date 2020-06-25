import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITaskCardFile, TaskCardFile } from 'app/shared/model/task-card-file.model';
import { TaskCardFileService } from './task-card-file.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from 'app/entities/task-card/task-card.service';

@Component({
  selector: 'jhi-task-card-file-update',
  templateUrl: './task-card-file-update.component.html',
})
export class TaskCardFileUpdateComponent implements OnInit {
  isSaving = false;
  taskcards: ITaskCard[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    dataContentType: [],
    creationDate: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    taskCardId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected taskCardFileService: TaskCardFileService,
    protected taskCardService: TaskCardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardFile }) => {
      if (!taskCardFile.id) {
        const today = moment().startOf('day');
        taskCardFile.creationDate = today;
        taskCardFile.modificationDate = today;
      }

      this.updateForm(taskCardFile);

      this.taskCardService.query().subscribe((res: HttpResponse<ITaskCard[]>) => (this.taskcards = res.body || []));
    });
  }

  updateForm(taskCardFile: ITaskCardFile): void {
    this.editForm.patchValue({
      id: taskCardFile.id,
      data: taskCardFile.data,
      dataContentType: taskCardFile.dataContentType,
      creationDate: taskCardFile.creationDate ? taskCardFile.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: taskCardFile.modificationDate ? taskCardFile.modificationDate.format(DATE_TIME_FORMAT) : null,
      taskCardId: taskCardFile.taskCardId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('simpleKanbanApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskCardFile = this.createFromForm();
    if (taskCardFile.id !== undefined) {
      this.subscribeToSaveResponse(this.taskCardFileService.update(taskCardFile));
    } else {
      this.subscribeToSaveResponse(this.taskCardFileService.create(taskCardFile));
    }
  }

  private createFromForm(): ITaskCardFile {
    return {
      ...new TaskCardFile(),
      id: this.editForm.get(['id'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      taskCardId: this.editForm.get(['taskCardId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskCardFile>>): void {
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
