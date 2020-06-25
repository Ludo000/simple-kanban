import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITaskCardFile } from 'app/shared/model/task-card-file.model';

@Component({
  selector: 'jhi-task-card-file-detail',
  templateUrl: './task-card-file-detail.component.html',
})
export class TaskCardFileDetailComponent implements OnInit {
  taskCardFile: ITaskCardFile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardFile }) => (this.taskCardFile = taskCardFile));
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
