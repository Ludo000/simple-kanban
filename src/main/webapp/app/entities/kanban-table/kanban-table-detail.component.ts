import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKanbanTable } from 'app/shared/model/kanban-table.model';

@Component({
  selector: 'jhi-kanban-table-detail',
  templateUrl: './kanban-table-detail.component.html',
})
export class KanbanTableDetailComponent implements OnInit {
  kanbanTable: IKanbanTable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kanbanTable }) => (this.kanbanTable = kanbanTable));
  }

  previousState(): void {
    window.history.back();
  }
}
