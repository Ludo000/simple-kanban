import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { TaskCardComponent } from './task-card.component';
import { TaskCardDetailComponent } from './task-card-detail.component';
import { TaskCardUpdateComponent } from './task-card-update.component';
import { TaskCardDeleteDialogComponent } from './task-card-delete-dialog.component';
import { taskCardRoute } from './task-card.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(taskCardRoute)],
  declarations: [TaskCardComponent, TaskCardDetailComponent, TaskCardUpdateComponent, TaskCardDeleteDialogComponent],
  entryComponents: [TaskCardDeleteDialogComponent],
})
export class SimpleKanbanTaskCardModule {}
