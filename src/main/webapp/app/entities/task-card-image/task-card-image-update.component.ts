import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITaskCardImage, TaskCardImage } from 'app/shared/model/task-card-image.model';
import { TaskCardImageService } from './task-card-image.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from 'app/entities/task-card/task-card.service';

@Component({
  selector: 'jhi-task-card-image-update',
  templateUrl: './task-card-image-update.component.html',
})
export class TaskCardImageUpdateComponent implements OnInit {
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
    protected taskCardImageService: TaskCardImageService,
    protected taskCardService: TaskCardService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardImage }) => {
      if (!taskCardImage.id) {
        const today = moment().startOf('day');
        taskCardImage.creationDate = today;
        taskCardImage.modificationDate = today;
      }

      this.updateForm(taskCardImage);

      this.taskCardService.query().subscribe((res: HttpResponse<ITaskCard[]>) => (this.taskcards = res.body || []));
    });
  }

  updateForm(taskCardImage: ITaskCardImage): void {
    this.editForm.patchValue({
      id: taskCardImage.id,
      data: taskCardImage.data,
      dataContentType: taskCardImage.dataContentType,
      creationDate: taskCardImage.creationDate ? taskCardImage.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: taskCardImage.modificationDate ? taskCardImage.modificationDate.format(DATE_TIME_FORMAT) : null,
      taskCardId: taskCardImage.taskCardId,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskCardImage = this.createFromForm();
    if (taskCardImage.id !== undefined) {
      this.subscribeToSaveResponse(this.taskCardImageService.update(taskCardImage));
    } else {
      this.subscribeToSaveResponse(this.taskCardImageService.create(taskCardImage));
    }
  }

  private createFromForm(): ITaskCardImage {
    return {
      ...new TaskCardImage(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskCardImage>>): void {
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
