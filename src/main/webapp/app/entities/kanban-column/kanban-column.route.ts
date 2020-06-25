import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKanbanColumn, KanbanColumn } from 'app/shared/model/kanban-column.model';
import { KanbanColumnService } from './kanban-column.service';
import { KanbanColumnComponent } from './kanban-column.component';
import { KanbanColumnDetailComponent } from './kanban-column-detail.component';
import { KanbanColumnUpdateComponent } from './kanban-column-update.component';

@Injectable({ providedIn: 'root' })
export class KanbanColumnResolve implements Resolve<IKanbanColumn> {
  constructor(private service: KanbanColumnService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKanbanColumn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kanbanColumn: HttpResponse<KanbanColumn>) => {
          if (kanbanColumn.body) {
            return of(kanbanColumn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KanbanColumn());
  }
}

export const kanbanColumnRoute: Routes = [
  {
    path: '',
    component: KanbanColumnComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.kanbanColumn.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KanbanColumnDetailComponent,
    resolve: {
      kanbanColumn: KanbanColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanColumn.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KanbanColumnUpdateComponent,
    resolve: {
      kanbanColumn: KanbanColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanColumn.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KanbanColumnUpdateComponent,
    resolve: {
      kanbanColumn: KanbanColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanColumn.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
