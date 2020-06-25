import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKanbanTable } from 'app/shared/model/kanban-table.model';

type EntityResponseType = HttpResponse<IKanbanTable>;
type EntityArrayResponseType = HttpResponse<IKanbanTable[]>;

@Injectable({ providedIn: 'root' })
export class KanbanTableService {
  public resourceUrl = SERVER_API_URL + 'api/kanban-tables';

  constructor(protected http: HttpClient) {}

  create(kanbanTable: IKanbanTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kanbanTable);
    return this.http
      .post<IKanbanTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(kanbanTable: IKanbanTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kanbanTable);
    return this.http
      .put<IKanbanTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IKanbanTable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKanbanTable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByUserIsCurrentUser(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKanbanTable[]>(`${this.resourceUrl}-by-user-is-current-user`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(kanbanTable: IKanbanTable): IKanbanTable {
    const copy: IKanbanTable = Object.assign({}, kanbanTable, {
      creationDate: kanbanTable.creationDate && kanbanTable.creationDate.isValid() ? kanbanTable.creationDate.toJSON() : undefined,
      modificationDate:
        kanbanTable.modificationDate && kanbanTable.modificationDate.isValid() ? kanbanTable.modificationDate.toJSON() : undefined,
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
      res.body.forEach((kanbanTable: IKanbanTable) => {
        kanbanTable.creationDate = kanbanTable.creationDate ? moment(kanbanTable.creationDate) : undefined;
        kanbanTable.modificationDate = kanbanTable.modificationDate ? moment(kanbanTable.modificationDate) : undefined;
      });
    }
    return res;
  }
}
