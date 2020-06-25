import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardImageDetailComponent } from 'app/entities/task-card-image/task-card-image-detail.component';
import { TaskCardImage } from 'app/shared/model/task-card-image.model';

describe('Component Tests', () => {
  describe('TaskCardImage Management Detail Component', () => {
    let comp: TaskCardImageDetailComponent;
    let fixture: ComponentFixture<TaskCardImageDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ taskCardImage: new TaskCardImage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardImageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskCardImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCardImageDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load taskCardImage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskCardImage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
