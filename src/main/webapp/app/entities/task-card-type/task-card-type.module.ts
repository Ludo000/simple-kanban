import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { TaskCardTypeComponent } from './task-card-type.component';
import { TaskCardTypeDetailComponent } from './task-card-type-detail.component';
import { TaskCardTypeUpdateComponent } from './task-card-type-update.component';
import { TaskCardTypeDeleteDialogComponent } from './task-card-type-delete-dialog.component';
import { taskCardTypeRoute } from './task-card-type.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(taskCardTypeRoute)],
  declarations: [TaskCardTypeComponent, TaskCardTypeDetailComponent, TaskCardTypeUpdateComponent, TaskCardTypeDeleteDialogComponent],
  entryComponents: [TaskCardTypeDeleteDialogComponent],
})
export class SimpleKanbanTaskCardTypeModule {}
