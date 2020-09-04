import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Observable } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { KanbanTable, IKanbanTable } from 'app/shared/model/kanban-table.model';
import { KanbanTableService } from 'app/entities/kanban-table/kanban-table.service';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  tables: KanbanTable[] | null = [];
  isInputCardShowed = false;
  isSaving = false;
  userIsConnected = false;
  noProject = true;

  editForm = this.fb.group({
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(255)]],
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private kanbanTableService: KanbanTableService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      this.loadAll();
    });
  }

  loadAll(): void {
    if (this.account) {
      this.userIsConnected = true;
      this.kanbanTableService.queryByUserIsCurrentUser().subscribe(data => {
        this.tables = data.body;
        this.noProject = this.tables?.length === 0;
      });
    }
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  clickOnCreateBtn(): void {
    this.isInputCardShowed = true;
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
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      creationDate: moment(),
      modificationDate: moment(),
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

  previousState(): void {
    this.loadAll();
    this.isInputCardShowed = false;
    this.editForm.reset();
  }

  delete(kanbanTable: KanbanTable): void {
    if (kanbanTable && kanbanTable.id) {
      this.subscribeToSaveResponse(this.kanbanTableService.delete(kanbanTable.id));
    }
  }
}
