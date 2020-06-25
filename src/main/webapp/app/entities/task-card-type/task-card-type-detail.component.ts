import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskCardType } from 'app/shared/model/task-card-type.model';

@Component({
  selector: 'jhi-task-card-type-detail',
  templateUrl: './task-card-type-detail.component.html',
})
export class TaskCardTypeDetailComponent implements OnInit {
  taskCardType: ITaskCardType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCardType }) => (this.taskCardType = taskCardType));
  }

  previousState(): void {
    window.history.back();
  }
}
