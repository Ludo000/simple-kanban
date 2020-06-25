import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHistory, History } from 'app/shared/model/history.model';
import { HistoryService } from './history.service';

@Component({
  selector: 'jhi-history-update',
  templateUrl: './history-update.component.html',
})
export class HistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    entry: [null, [Validators.required]],
    entryDate: [null, [Validators.required]],
  });

  constructor(protected historyService: HistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ history }) => {
      if (!history.id) {
        const today = moment().startOf('day');
        history.entryDate = today;
      }

      this.updateForm(history);
    });
  }

  updateForm(history: IHistory): void {
    this.editForm.patchValue({
      id: history.id,
      entry: history.entry,
      entryDate: history.entryDate ? history.entryDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const history = this.createFromForm();
    if (history.id !== undefined) {
      this.subscribeToSaveResponse(this.historyService.update(history));
    } else {
      this.subscribeToSaveResponse(this.historyService.create(history));
    }
  }

  private createFromForm(): IHistory {
    return {
      ...new History(),
      id: this.editForm.get(['id'])!.value,
      entry: this.editForm.get(['entry'])!.value,
      entryDate: this.editForm.get(['entryDate'])!.value ? moment(this.editForm.get(['entryDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistory>>): void {
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
}
