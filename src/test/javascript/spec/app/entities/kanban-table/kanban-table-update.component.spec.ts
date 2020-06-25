import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { KanbanTableUpdateComponent } from 'app/entities/kanban-table/kanban-table-update.component';
import { KanbanTableService } from 'app/entities/kanban-table/kanban-table.service';
import { KanbanTable } from 'app/shared/model/kanban-table.model';

describe('Component Tests', () => {
  describe('KanbanTable Management Update Component', () => {
    let comp: KanbanTableUpdateComponent;
    let fixture: ComponentFixture<KanbanTableUpdateComponent>;
    let service: KanbanTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [KanbanTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KanbanTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KanbanTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KanbanTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KanbanTable(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new KanbanTable();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
