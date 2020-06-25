import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardFileUpdateComponent } from 'app/entities/task-card-file/task-card-file-update.component';
import { TaskCardFileService } from 'app/entities/task-card-file/task-card-file.service';
import { TaskCardFile } from 'app/shared/model/task-card-file.model';

describe('Component Tests', () => {
  describe('TaskCardFile Management Update Component', () => {
    let comp: TaskCardFileUpdateComponent;
    let fixture: ComponentFixture<TaskCardFileUpdateComponent>;
    let service: TaskCardFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardFileUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskCardFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCardFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCardFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskCardFile(123);
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
        const entity = new TaskCardFile();
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
