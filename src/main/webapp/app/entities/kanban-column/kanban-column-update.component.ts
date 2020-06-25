import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IKanbanColumn, KanbanColumn } from 'app/shared/model/kanban-column.model';
import { KanbanColumnService } from './kanban-column.service';
import { IKanbanTable } from 'app/shared/model/kanban-table.model';
import { KanbanTableService } from 'app/entities/kanban-table/kanban-table.service';

@Component({
  selector: 'jhi-kanban-column-update',
  templateUrl: './kanban-column-update.component.html',
})
export class KanbanColumnUpdateComponent implements OnInit {
  isSaving = false;
  kanbantables: IKanbanTable[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    creationDate: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    kanbanTableId: [],
  });

  constructor(
    protected kanbanColumnService: KanbanColumnService,
    protected kanbanTableService: KanbanTableService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kanbanColumn }) => {
      if (!kanbanColumn.id) {
        const today = moment().startOf('day');
        kanbanColumn.creationDate = today;
        kanbanColumn.modificationDate = today;
      }

      this.updateForm(kanbanColumn);

      this.kanbanTableService.query().subscribe((res: HttpResponse<IKanbanTable[]>) => (this.kanbantables = res.body || []));
    });
  }

  updateForm(kanbanColumn: IKanbanColumn): void {
    this.editForm.patchValue({
      id: kanbanColumn.id,
      name: kanbanColumn.name,
      creationDate: kanbanColumn.creationDate ? kanbanColumn.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: kanbanColumn.modificationDate ? kanbanColumn.modificationDate.format(DATE_TIME_FORMAT) : null,
      kanbanTableId: kanbanColumn.kanbanTableId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kanbanColumn = this.createFromForm();
    if (kanbanColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.kanbanColumnService.update(kanbanColumn));
    } else {
      this.subscribeToSaveResponse(this.kanbanColumnService.create(kanbanColumn));
    }
  }

  private createFromForm(): IKanbanColumn {
    return {
      ...new KanbanColumn(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      kanbanTableId: this.editForm.get(['kanbanTableId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKanbanColumn>>): void {
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

  trackById(index: number, item: IKanbanTable): any {
    return item.id;
  }
}
