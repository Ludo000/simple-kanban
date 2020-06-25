import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskCardFile } from 'app/shared/model/task-card-file.model';
import { TaskCardFileService } from './task-card-file.service';

@Component({
  templateUrl: './task-card-file-delete-dialog.component.html',
})
export class TaskCardFileDeleteDialogComponent {
  taskCardFile?: ITaskCardFile;

  constructor(
    protected taskCardFileService: TaskCardFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskCardFileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskCardFileListModification');
      this.activeModal.close();
    });
  }
}
