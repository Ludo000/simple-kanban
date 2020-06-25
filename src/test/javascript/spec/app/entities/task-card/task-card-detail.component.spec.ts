import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { TaskCardDetailComponent } from 'app/entities/task-card/task-card-detail.component';
import { TaskCard } from 'app/shared/model/task-card.model';

describe('Component Tests', () => {
  describe('TaskCard Management Detail Component', () => {
    let comp: TaskCardDetailComponent;
    let fixture: ComponentFixture<TaskCardDetailComponent>;
    const route = ({ data: of({ taskCard: new TaskCard(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [TaskCardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskCardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskCard on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskCard).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
