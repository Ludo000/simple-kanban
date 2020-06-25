import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardTypeDetailComponent } from 'app/entities/task-card-type/task-card-type-detail.component';
import { TaskCardType } from 'app/shared/model/task-card-type.model';

describe('Component Tests', () => {
  describe('TaskCardType Management Detail Component', () => {
    let comp: TaskCardTypeDetailComponent;
    let fixture: ComponentFixture<TaskCardTypeDetailComponent>;
    const route = ({ data: of({ taskCardType: new TaskCardType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskCardTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCardTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskCardType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskCardType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
