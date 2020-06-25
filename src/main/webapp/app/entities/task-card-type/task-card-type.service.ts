import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskCardType } from 'app/shared/model/task-card-type.model';

type EntityResponseType = HttpResponse<ITaskCardType>;
type EntityArrayResponseType = HttpResponse<ITaskCardType[]>;

@Injectable({ providedIn: 'root' })
export class TaskCardTypeService {
  public resourceUrl = SERVER_API_URL + 'api/task-card-types';

  constructor(protected http: HttpClient) {}

  create(taskCardType: ITaskCardType): Observable<EntityResponseType> {
    return this.http.post<ITaskCardType>(this.resourceUrl, taskCardType, { observe: 'response' });
  }

  update(taskCardType: ITaskCardType): Observable<EntityResponseType> {
    return this.http.put<ITaskCardType>(this.resourceUrl, taskCardType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaskCardType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaskCardType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
