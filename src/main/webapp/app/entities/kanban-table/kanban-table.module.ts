import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleKanbanSharedModule } from 'app/shared/shared.module';
import { KanbanTableComponent } from './kanban-table.component';
import { KanbanTableDetailComponent } from './kanban-table-detail.component';
import { KanbanTableUpdateComponent } from './kanban-table-update.component';
import { KanbanTableDeleteDialogComponent } from './kanban-table-delete-dialog.component';
import { kanbanTableRoute } from './kanban-table.route';

@NgModule({
  imports: [SimpleKanbanSharedModule, RouterModule.forChild(kanbanTableRoute)],
  declarations: [KanbanTableComponent, KanbanTableDetailComponent, KanbanTableUpdateComponent, KanbanTableDeleteDialogComponent],
  entryComponents: [KanbanTableDeleteDialogComponent],
})
export class SimpleKanbanKanbanTableModule {}
