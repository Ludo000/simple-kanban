import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { KanbanColumnComponent } from './kanban-column.component';
import { KanbanColumnDetailComponent } from './kanban-column-detail.component';
import { KanbanColumnUpdateComponent } from './kanban-column-update.component';
import { KanbanColumnDeleteDialogComponent } from './kanban-column-delete-dialog.component';
import { kanbanColumnRoute } from './kanban-column.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(kanbanColumnRoute)],
  declarations: [KanbanColumnComponent, KanbanColumnDetailComponent, KanbanColumnUpdateComponent, KanbanColumnDeleteDialogComponent],
  entryComponents: [KanbanColumnDeleteDialogComponent],
})
export class SimpleKanbanKanbanColumnModule {}
