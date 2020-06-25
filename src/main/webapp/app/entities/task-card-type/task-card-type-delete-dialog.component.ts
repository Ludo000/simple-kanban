import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskCardType } from 'app/shared/model/task-card-type.model';
import { TaskCardTypeService } from './task-card-type.service';

@Component({
  templateUrl: './task-card-type-delete-dialog.component.html',
})
export class TaskCardTypeDeleteDialogComponent {
  taskCardType?: ITaskCardType;

  constructor(
    protected taskCardTypeService: TaskCardTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskCardTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskCardTypeListModification');
      this.activeModal.close();
    });
  }
}
