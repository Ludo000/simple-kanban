import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskCardType, TaskCardType } from 'app/shared/model/task-card-type.model';
import { TaskCardTypeService } from './task-card-type.service';
import { TaskCardTypeComponent } from './task-card-type.component';
import { TaskCardTypeDetailComponent } from './task-card-type-detail.component';
import { TaskCardTypeUpdateComponent } from './task-card-type-update.component';

@Injectable({ providedIn: 'root' })
export class TaskCardTypeResolve implements Resolve<ITaskCardType> {
  constructor(private service: TaskCardTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskCardType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskCardType: HttpResponse<TaskCardType>) => {
          if (taskCardType.body) {
            return of(taskCardType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskCardType());
  }
}

export const taskCardTypeRoute: Routes = [
  {
    path: '',
    component: TaskCardTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.taskCardType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskCardTypeDetailComponent,
    resolve: {
      taskCardType: TaskCardTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskCardTypeUpdateComponent,
    resolve: {
      taskCardType: TaskCardTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskCardTypeUpdateComponent,
    resolve: {
      taskCardType: TaskCardTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
