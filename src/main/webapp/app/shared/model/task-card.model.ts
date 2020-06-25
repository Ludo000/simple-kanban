import { Moment } from 'moment';
import { ITaskCardType } from 'app/shared/model/task-card-type.model';
import { ITaskCardImage } from 'app/shared/model/task-card-image.model';
import { ITaskCardFile } from 'app/shared/model/task-card-file.model';
import { IComment } from 'app/shared/model/comment.model';

export interface ITaskCard {
  id?: number;
  name?: string;
  description?: string;
  colorHexCode?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
  limitDate?: Moment;
  types?: ITaskCardType[];
  images?: ITaskCardImage[];
  files?: ITaskCardFile[];
  comments?: IComment[];
  kanbanColumnId?: number;
}

export class TaskCard implements ITaskCard {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public colorHexCode?: string,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public limitDate?: Moment,
    public types?: ITaskCardType[],
    public images?: ITaskCardImage[],
    public files?: ITaskCardFile[],
    public comments?: IComment[],
    public kanbanColumnId?: number
  ) {}
}
