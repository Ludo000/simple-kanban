import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from './task-card.service';

@Component({
  templateUrl: './task-card-delete-dialog.component.html',
})
export class TaskCardDeleteDialogComponent {
  taskCard?: ITaskCard;

  constructor(protected taskCardService: TaskCardService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskCardService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskCardListModification');
      this.activeModal.close();
    });
  }
}
