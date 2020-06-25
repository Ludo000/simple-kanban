import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { TaskCardImageComponent } from './task-card-image.component';
import { TaskCardImageDetailComponent } from './task-card-image-detail.component';
import { TaskCardImageUpdateComponent } from './task-card-image-update.component';
import { TaskCardImageDeleteDialogComponent } from './task-card-image-delete-dialog.component';
import { taskCardImageRoute } from './task-card-image.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(taskCardImageRoute)],
  declarations: [TaskCardImageComponent, TaskCardImageDetailComponent, TaskCardImageUpdateComponent, TaskCardImageDeleteDialogComponent],
  entryComponents: [TaskCardImageDeleteDialogComponent],
})
export class SimpleKanbanTaskCardImageModule {}
