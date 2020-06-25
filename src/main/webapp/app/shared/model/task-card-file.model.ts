import { Moment } from 'moment';

export interface ITaskCardFile {
  id?: number;
  dataContentType?: string;
  data?: any;
  creationDate?: Moment;
  modificationDate?: Moment;
  taskCardId?: number;
}

export class TaskCardFile implements ITaskCardFile {
  constructor(
    public id?: number,
    public dataContentType?: string,
    public data?: any,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public taskCardId?: number
  ) {}
}
