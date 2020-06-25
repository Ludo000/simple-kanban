import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { TaskCardFileComponent } from './task-card-file.component';
import { TaskCardFileDetailComponent } from './task-card-file-detail.component';
import { TaskCardFileUpdateComponent } from './task-card-file-update.component';
import { TaskCardFileDeleteDialogComponent } from './task-card-file-delete-dialog.component';
import { taskCardFileRoute } from './task-card-file.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(taskCardFileRoute)],
  declarations: [TaskCardFileComponent, TaskCardFileDetailComponent, TaskCardFileUpdateComponent, TaskCardFileDeleteDialogComponent],
  entryComponents: [TaskCardFileDeleteDialogComponent],
})
export class SimpleKanbanTaskCardFileModule {}
