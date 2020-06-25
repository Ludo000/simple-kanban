import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKanbanColumn } from 'app/shared/model/kanban-column.model';

@Component({
  selector: 'jhi-kanban-column-detail',
  templateUrl: './kanban-column-detail.component.html',
})
export class KanbanColumnDetailComponent implements OnInit {
  kanbanColumn: IKanbanColumn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kanbanColumn }) => (this.kanbanColumn = kanbanColumn));
  }

  previousState(): void {
    window.history.back();
  }
}
