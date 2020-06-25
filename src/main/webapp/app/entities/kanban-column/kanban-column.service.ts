import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKanbanColumn } from 'app/shared/model/kanban-column.model';

type EntityResponseType = HttpResponse<IKanbanColumn>;
type EntityArrayResponseType = HttpResponse<IKanbanColumn[]>;

@Injectable({ providedIn: 'root' })
export class KanbanColumnService {
  public resourceUrl = SERVER_API_URL + 'api/kanban-columns';

  constructor(protected http: HttpClient) {}

  create(kanbanColumn: IKanbanColumn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kanbanColumn);
    return this.http
      .post<IKanbanColumn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(kanbanColumn: IKanbanColumn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kanbanColumn);
    return this.http
      .put<IKanbanColumn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IKanbanColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKanbanColumn[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(kanbanColumn: IKanbanColumn): IKanbanColumn {
    const copy: IKanbanColumn = Object.assign({}, kanbanColumn, {
      creationDate: kanbanColumn.creationDate && kanbanColumn.creationDate.isValid() ? kanbanColumn.creationDate.toJSON() : undefined,
      modificationDate:
        kanbanColumn.modificationDate && kanbanColumn.modificationDate.isValid() ? kanbanColumn.modificationDate.toJSON() : undefined,
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
      res.body.forEach((kanbanColumn: IKanbanColumn) => {
        kanbanColumn.creationDate = kanbanColumn.creationDate ? moment(kanbanColumn.creationDate) : undefined;
        kanbanColumn.modificationDate = kanbanColumn.modificationDate ? moment(kanbanColumn.modificationDate) : undefined;
      });
    }
    return res;
  }
}
