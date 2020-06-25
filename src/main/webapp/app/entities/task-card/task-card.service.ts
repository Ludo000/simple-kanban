import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskCard } from 'app/shared/model/task-card.model';

type EntityResponseType = HttpResponse<ITaskCard>;
type EntityArrayResponseType = HttpResponse<ITaskCard[]>;

@Injectable({ providedIn: 'root' })
export class TaskCardService {
  public resourceUrl = SERVER_API_URL + 'api/task-cards';

  constructor(protected http: HttpClient) {}

  create(taskCard: ITaskCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCard);
    return this.http
      .post<ITaskCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskCard: ITaskCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCard);
    return this.http
      .put<ITaskCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskCard: ITaskCard): ITaskCard {
    const copy: ITaskCard = Object.assign({}, taskCard, {
      creationDate: taskCard.creationDate && taskCard.creationDate.isValid() ? taskCard.creationDate.toJSON() : undefined,
      modificationDate: taskCard.modificationDate && taskCard.modificationDate.isValid() ? taskCard.modificationDate.toJSON() : undefined,
      limitDate: taskCard.limitDate && taskCard.limitDate.isValid() ? taskCard.limitDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.modificationDate = res.body.modificationDate ? moment(res.body.modificationDate) : undefined;
      res.body.limitDate = res.body.limitDate ? moment(res.body.limitDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskCard: ITaskCard) => {
        taskCard.creationDate = taskCard.creationDate ? moment(taskCard.creationDate) : undefined;
        taskCard.modificationDate = taskCard.modificationDate ? moment(taskCard.modificationDate) : undefined;
        taskCard.limitDate = taskCard.limitDate ? moment(taskCard.limitDate) : undefined;
      });
    }
    return res;
  }
}
