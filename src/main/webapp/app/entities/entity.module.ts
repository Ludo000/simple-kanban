import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'kanban-table',
        loadChildren: () => import('./kanban-table/kanban-table.module').then(m => m.SimpleKanbanKanbanTableModule),
      },
      {
        path: 'kanban-column',
        loadChildren: () => import('./kanban-column/kanban-column.module').then(m => m.SimpleKanbanKanbanColumnModule),
      },
      {
        path: 'task-card',
        loadChildren: () => import('./task-card/task-card.module').then(m => m.SimpleKanbanTaskCardModule),
      },
      {
        path: 'task-card-type',
        loadChildren: () => import('./task-card-type/task-card-type.module').then(m => m.SimpleKanbanTaskCardTypeModule),
      },
      {
        path: 'task-card-image',
        loadChildren: () => import('./task-card-image/task-card-image.module').then(m => m.SimpleKanbanTaskCardImageModule),
      },
      {
        path: 'task-card-file',
        loadChildren: () => import('./task-card-file/task-card-file.module').then(m => m.SimpleKanbanTaskCardFileModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.SimpleKanbanCommentModule),
      },
      {
        path: 'history',
        loadChildren: () => import('./history/history.module').then(m => m.SimpleKanbanHistoryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SimpleKanbanEntityModule {}
