import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskCardFile, TaskCardFile } from 'app/shared/model/task-card-file.model';
import { TaskCardFileService } from './task-card-file.service';
import { TaskCardFileComponent } from './task-card-file.component';
import { TaskCardFileDetailComponent } from './task-card-file-detail.component';
import { TaskCardFileUpdateComponent } from './task-card-file-update.component';

@Injectable({ providedIn: 'root' })
export class TaskCardFileResolve implements Resolve<ITaskCardFile> {
  constructor(private service: TaskCardFileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskCardFile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskCardFile: HttpResponse<TaskCardFile>) => {
          if (taskCardFile.body) {
            return of(taskCardFile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskCardFile());
  }
}

export const taskCardFileRoute: Routes = [
  {
    path: '',
    component: TaskCardFileComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'simpleKanbanApp.taskCardFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskCardFileDetailComponent,
    resolve: {
      taskCardFile: TaskCardFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskCardFileUpdateComponent,
    resolve: {
      taskCardFile: TaskCardFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskCardFileUpdateComponent,
    resolve: {
      taskCardFile: TaskCardFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleKanbanApp.taskCardFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
