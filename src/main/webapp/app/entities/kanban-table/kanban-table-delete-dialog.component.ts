import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKanbanTable } from 'app/shared/model/kanban-table.model';
import { KanbanTableService } from './kanban-table.service';

@Component({
  templateUrl: './kanban-table-delete-dialog.component.html',
})
export class KanbanTableDeleteDialogComponent {
  kanbanTable?: IKanbanTable;

  constructor(
    protected kanbanTableService: KanbanTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kanbanTableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kanbanTableListModification');
      this.activeModal.close();
    });
  }
}
