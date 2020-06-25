import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleKanbanTestModule } from '../../../test.module';
import { KanbanTableDetailComponent } from 'app/entities/kanban-table/kanban-table-detail.component';
import { KanbanTable } from 'app/shared/model/kanban-table.model';

describe('Component Tests', () => {
  describe('KanbanTable Management Detail Component', () => {
    let comp: KanbanTableDetailComponent;
    let fixture: ComponentFixture<KanbanTableDetailComponent>;
    const route = ({ data: of({ kanbanTable: new KanbanTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleKanbanTestModule],
        declarations: [KanbanTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KanbanTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KanbanTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kanbanTable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kanbanTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
