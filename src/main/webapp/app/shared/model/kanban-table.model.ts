import { Moment } from 'moment';
import { IKanbanColumn } from 'app/shared/model/kanban-column.model';

export interface IKanbanTable {
  id?: number;
  name?: string;
  description?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
  tables?: IKanbanColumn[];
  userId?: number;
}

export class KanbanTable implements IKanbanTable {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public tables?: IKanbanColumn[],
    public userId?: number
  ) {}
}
