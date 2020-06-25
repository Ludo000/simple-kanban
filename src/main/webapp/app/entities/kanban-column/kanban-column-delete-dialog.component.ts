import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKanbanColumn } from 'app/shared/model/kanban-column.model';
import { KanbanColumnService } from './kanban-column.service';

@Component({
  templateUrl: './kanban-column-delete-dialog.component.html',
})
export class KanbanColumnDeleteDialogComponent {
  kanbanColumn?: IKanbanColumn;

  constructor(
    protected kanbanColumnService: KanbanColumnService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kanbanColumnService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kanbanColumnListModification');
      this.activeModal.close();
    });
  }
}
