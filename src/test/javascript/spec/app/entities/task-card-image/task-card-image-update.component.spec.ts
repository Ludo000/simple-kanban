import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardImageUpdateComponent } from 'app/entities/task-card-image/task-card-image-update.component';
import { TaskCardImageService } from 'app/entities/task-card-image/task-card-image.service';
import { TaskCardImage } from 'app/shared/model/task-card-image.model';

describe('Component Tests', () => {
  describe('TaskCardImage Management Update Component', () => {
    let comp: TaskCardImageUpdateComponent;
    let fixture: ComponentFixture<TaskCardImageUpdateComponent>;
    let service: TaskCardImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardImageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskCardImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCardImageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCardImageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskCardImage(123);
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
        const entity = new TaskCardImage();
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
