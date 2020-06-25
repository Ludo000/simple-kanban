import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskCardFile } from 'app/shared/model/task-card-file.model';

type EntityResponseType = HttpResponse<ITaskCardFile>;
type EntityArrayResponseType = HttpResponse<ITaskCardFile[]>;

@Injectable({ providedIn: 'root' })
export class TaskCardFileService {
  public resourceUrl = SERVER_API_URL + 'api/task-card-files';

  constructor(protected http: HttpClient) {}

  create(taskCardFile: ITaskCardFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCardFile);
    return this.http
      .post<ITaskCardFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskCardFile: ITaskCardFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCardFile);
    return this.http
      .put<ITaskCardFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskCardFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskCardFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskCardFile: ITaskCardFile): ITaskCardFile {
    const copy: ITaskCardFile = Object.assign({}, taskCardFile, {
      creationDate: taskCardFile.creationDate && taskCardFile.creationDate.isValid() ? taskCardFile.creationDate.toJSON() : undefined,
      modificationDate:
        taskCardFile.modificationDate && taskCardFile.modificationDate.isValid() ? taskCardFile.modificationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.modificationDate = res.body.modificationDate ? moment(res.body.modificationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskCardFile: ITaskCardFile) => {
        taskCardFile.creationDate = taskCardFile.creationDate ? moment(taskCardFile.creationDate) : undefined;
        taskCardFile.modificationDate = taskCardFile.modificationDate ? moment(taskCardFile.modificationDate) : undefined;
      });
    }
    return res;
  }
}
