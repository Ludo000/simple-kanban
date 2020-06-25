import { Moment } from 'moment';

export interface IComment {
  id?: number;
  content?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
  taskCardId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public content?: string,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public taskCardId?: number
  ) {}
}
