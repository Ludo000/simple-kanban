import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskCardImage, TaskCardImage } from 'app/shared/model/task-card-image.model';
import { TaskCardImageService } from './task-card-image.service';
import { TaskCardImageComponent } from './task-card-image.component';
import { TaskCardImageDetailComponent } from './task-card-image-detail.component';
import { TaskCardImageUpdateComponent } from './task-card-image-update.component';

@Injectable({ providedIn: 'root' })
export class TaskCardImageResolve implements Resolve<ITaskCardImage> {
  constructor(private service: TaskCardImageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskCardImage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskCardImage: HttpResponse<TaskCardImage>) => {
          if (taskCardImage.body) {
            return of(taskCardImage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskCardImage());
  }
}

export const taskCardImageRoute: Routes = [
  {
    path: '',
    component: TaskCardImageComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.taskCardImage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskCardImageDetailComponent,
    resolve: {
      taskCardImage: TaskCardImageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardImage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskCardImageUpdateComponent,
    resolve: {
      taskCardImage: TaskCardImageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardImage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskCardImageUpdateComponent,
    resolve: {
      taskCardImage: TaskCardImageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardImage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
