import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskCardImage } from 'app/shared/model/task-card-image.model';
import { TaskCardImageService } from './task-card-image.service';

@Component({
  templateUrl: './task-card-image-delete-dialog.component.html',
})
export class TaskCardImageDeleteDialogComponent {
  taskCardImage?: ITaskCardImage;

  constructor(
    protected taskCardImageService: TaskCardImageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskCardImageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskCardImageListModification');
      this.activeModal.close();
    });
  }
}
