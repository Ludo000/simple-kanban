export interface ITaskCardType {
  id?: number;
  name?: string;
  taskCardId?: number;
}

export class TaskCardType implements ITaskCardType {
  constructor(public id?: number, public name?: string, public taskCardId?: number) {}
}
