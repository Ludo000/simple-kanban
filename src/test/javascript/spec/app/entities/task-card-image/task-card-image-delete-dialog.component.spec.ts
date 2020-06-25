import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SimpleKanbanTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TaskCardImageDeleteDialogComponent } from 'app/entities/task-card-image/task-card-image-delete-dialog.component';
import { TaskCardImageService } from 'app/entities/task-card-image/task-card-image.service';

describe('Component Tests', () => {
  describe('TaskCardImage Management Delete Component', () => {
    let comp: TaskCardImageDeleteDialogComponent;
    let fixture: ComponentFixture<TaskCardImageDeleteDialogComponent>;
    let service: TaskCardImageService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardImageDeleteDialogComponent],
      })
        .overrideTemplate(TaskCardImageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCardImageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCardImageService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
