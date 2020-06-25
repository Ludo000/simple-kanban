import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITaskCardImage } from 'app/shared/model/task-card-image.model';

@Component({
  selector: 'jhi-task-card-image-detail',
  templateUrl: './task-card-image-detail.component.html',
})
export class TaskCardImageDetailComponent implements OnInit {
  taskCardImage: ITaskCardImage | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardImage }) => (this.taskCardImage = taskCardImage));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
