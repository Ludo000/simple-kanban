import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskCardImage } from 'app/shared/model/task-card-image.model';

type EntityResponseType = HttpResponse<ITaskCardImage>;
type EntityArrayResponseType = HttpResponse<ITaskCardImage[]>;

@Injectable({ providedIn: 'root' })
export class TaskCardImageService {
  public resourceUrl = SERVER_API_URL + 'api/task-card-images';

  constructor(protected http: HttpClient) {}

  create(taskCardImage: ITaskCardImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCardImage);
    return this.http
      .post<ITaskCardImage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskCardImage: ITaskCardImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCardImage);
    return this.http
      .put<ITaskCardImage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskCardImage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskCardImage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskCardImage: ITaskCardImage): ITaskCardImage {
    const copy: ITaskCardImage = Object.assign({}, taskCardImage, {
      creationDate: taskCardImage.creationDate && taskCardImage.creationDate.isValid() ? taskCardImage.creationDate.toJSON() : undefined,
      modificationDate:
        taskCardImage.modificationDate && taskCardImage.modificationDate.isValid() ? taskCardImage.modificationDate.toJSON() : undefined,
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
      res.body.forEach((taskCardImage: ITaskCardImage) => {
        taskCardImage.creationDate = taskCardImage.creationDate ? moment(taskCardImage.creationDate) : undefined;
        taskCardImage.modificationDate = taskCardImage.modificationDate ? moment(taskCardImage.modificationDate) : undefined;
      });
    }
    return res;
  }
}
