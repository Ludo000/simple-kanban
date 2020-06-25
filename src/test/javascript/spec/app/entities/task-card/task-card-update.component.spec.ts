import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardUpdateComponent } from 'app/entities/task-card/task-card-update.component';
import { TaskCardService } from 'app/entities/task-card/task-card.service';
import { TaskCard } from 'app/shared/model/task-card.model';

describe('Component Tests', () => {
  describe('TaskCard Management Update Component', () => {
    let comp: TaskCardUpdateComponent;
    let fixture: ComponentFixture<TaskCardUpdateComponent>;
    let service: TaskCardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskCardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskCard(123);
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
        const entity = new TaskCard();
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
