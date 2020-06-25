import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardFileDetailComponent } from 'app/entities/task-card-file/task-card-file-detail.component';
import { TaskCardFile } from 'app/shared/model/task-card-file.model';

describe('Component Tests', () => {
  describe('TaskCardFile Management Detail Component', () => {
    let comp: TaskCardFileDetailComponent;
    let fixture: ComponentFixture<TaskCardFileDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ taskCardFile: new TaskCardFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskCardFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCardFileDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load taskCardFile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskCardFile).toEqual(jasmine.objectContaining({ id: 123 }));
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
