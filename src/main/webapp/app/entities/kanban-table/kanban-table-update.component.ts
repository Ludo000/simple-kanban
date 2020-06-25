import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IKanbanTable, KanbanTable } from 'app/shared/model/kanban-table.model';
import { KanbanTableService } from './kanban-table.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-kanban-table-update',
  templateUrl: './kanban-table-update.component.html',
})
export class KanbanTableUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    creationDate: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    userId: [],
  });

  constructor(
    protected kanbanTableService: KanbanTableService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kanbanTable }) => {
      if (!kanbanTable.id) {
        const today = moment().startOf('day');
        kanbanTable.creationDate = today;
        kanbanTable.modificationDate = today;
      }

      this.updateForm(kanbanTable);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(kanbanTable: IKanbanTable): void {
    this.editForm.patchValue({
      id: kanbanTable.id,
      name: kanbanTable.name,
      description: kanbanTable.description,
      creationDate: kanbanTable.creationDate ? kanbanTable.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: kanbanTable.modificationDate ? kanbanTable.modificationDate.format(DATE_TIME_FORMAT) : null,
      userId: kanbanTable.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kanbanTable = this.createFromForm();
    if (kanbanTable.id !== undefined) {
      this.subscribeToSaveResponse(this.kanbanTableService.update(kanbanTable));
    } else {
      this.subscribeToSaveResponse(this.kanbanTableService.create(kanbanTable));
    }
  }

  private createFromForm(): IKanbanTable {
    return {
      ...new KanbanTable(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKanbanTable>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
