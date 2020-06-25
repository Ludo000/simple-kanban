import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskCard, TaskCard } from 'app/shared/model/task-card.model';
import { TaskCardService } from './task-card.service';
import { TaskCardComponent } from './task-card.component';
import { TaskCardDetailComponent } from './task-card-detail.component';
import { TaskCardUpdateComponent } from './task-card-update.component';

@Injectable({ providedIn: 'root' })
export class TaskCardResolve implements Resolve<ITaskCard> {
  constructor(private service: TaskCardService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskCard> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskCard: HttpResponse<TaskCard>) => {
          if (taskCard.body) {
            return of(taskCard.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskCard());
  }
}

export const taskCardRoute: Routes = [
  {
    path: '',
    component: TaskCardComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.taskCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskCardDetailComponent,
    resolve: {
      taskCard: TaskCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskCardUpdateComponent,
    resolve: {
      taskCard: TaskCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskCardUpdateComponent,
    resolve: {
      taskCard: TaskCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
