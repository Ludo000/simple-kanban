import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { KanbanColumnDetailComponent } from 'app/entities/kanban-column/kanban-column-detail.component';
import { KanbanColumn } from 'app/shared/model/kanban-column.model';

describe('Component Tests', () => {
  describe('KanbanColumn Management Detail Component', () => {
    let comp: KanbanColumnDetailComponent;
    let fixture: ComponentFixture<KanbanColumnDetailComponent>;
    const route = ({ data: of({ kanbanColumn: new KanbanColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [KanbanColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KanbanColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KanbanColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kanbanColumn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kanbanColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
