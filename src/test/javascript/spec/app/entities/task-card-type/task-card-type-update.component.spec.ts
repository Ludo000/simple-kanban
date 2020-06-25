import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardTypeUpdateComponent } from 'app/entities/task-card-type/task-card-type-update.component';
import { TaskCardTypeService } from 'app/entities/task-card-type/task-card-type.service';
import { TaskCardType } from 'app/shared/model/task-card-type.model';

describe('Component Tests', () => {
  describe('TaskCardType Management Update Component', () => {
    let comp: TaskCardTypeUpdateComponent;
    let fixture: ComponentFixture<TaskCardTypeUpdateComponent>;
    let service: TaskCardTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskCardTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCardTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCardTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskCardType(123);
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
        const entity = new TaskCardType();
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
