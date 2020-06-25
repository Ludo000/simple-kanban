import { Moment } from 'moment';
import { ITaskCard } from 'app/shared/model/task-card.model';

export interface IKanbanColumn {
  id?: number;
  name?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
  columns?: ITaskCard[];
  kanbanTableId?: number;
}

export class KanbanColumn implements IKanbanColumn {
  constructor(
    public id?: number,
    public name?: string,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public columns?: ITaskCard[],
    public kanbanTableId?: number
  ) {}
}
