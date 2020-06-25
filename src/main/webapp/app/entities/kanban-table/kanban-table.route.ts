import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKanbanTable, KanbanTable } from 'app/shared/model/kanban-table.model';
import { KanbanTableService } from './kanban-table.service';
import { KanbanTableComponent } from './kanban-table.component';
import { KanbanTableDetailComponent } from './kanban-table-detail.component';
import { KanbanTableUpdateComponent } from './kanban-table-update.component';

@Injectable({ providedIn: 'root' })
export class KanbanTableResolve implements Resolve<IKanbanTable> {
  constructor(private service: KanbanTableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKanbanTable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kanbanTable: HttpResponse<KanbanTable>) => {
          if (kanbanTable.body) {
            return of(kanbanTable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KanbanTable());
  }
}

export const kanbanTableRoute: Routes = [
  {
    path: '',
    component: KanbanTableComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.kanbanTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KanbanTableDetailComponent,
    resolve: {
      kanbanTable: KanbanTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KanbanTableUpdateComponent,
    resolve: {
      kanbanTable: KanbanTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KanbanTableUpdateComponent,
    resolve: {
      kanbanTable: KanbanTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.kanbanTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
