import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskCard } from 'app/shared/model/task-card.model';

@Component({
  selector: 'jhi-task-card-detail',
  templateUrl: './task-card-detail.component.html',
})
export class TaskCardDetailComponent implements OnInit {
  taskCard: ITaskCard | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskCard }) => (this.taskCard = taskCard));
  }

  previousState(): void {
    window.history.back();
  }
}
