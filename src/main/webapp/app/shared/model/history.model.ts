import { Moment } from 'moment';

export interface IHistory {
  id?: number;
  entry?: string;
  entryDate?: Moment;
}

export class History implements IHistory {
  constructor(public id?: number, public entry?: string, public entryDate?: Moment) {}
}
