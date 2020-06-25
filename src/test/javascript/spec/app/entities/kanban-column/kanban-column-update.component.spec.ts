import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { KanbanColumnUpdateComponent } from 'app/entities/kanban-column/kanban-column-update.component';
import { KanbanColumnService } from 'app/entities/kanban-column/kanban-column.service';
import { KanbanColumn } from 'app/shared/model/kanban-column.model';

describe('Component Tests', () => {
  describe('KanbanColumn Management Update Component', () => {
    let comp: KanbanColumnUpdateComponent;
    let fixture: ComponentFixture<KanbanColumnUpdateComponent>;
    let service: KanbanColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [KanbanColumnUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KanbanColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KanbanColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KanbanColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KanbanColumn(123);
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
        const entity = new KanbanColumn();
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
