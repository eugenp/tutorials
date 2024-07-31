/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookstoreTestModule } from '../../../test.module';
import { BookDeleteDialogComponent } from 'app/entities/book/book-delete-dialog.component';
import { BookService } from 'app/entities/book/book.service';

describe('Component Tests', () => {
    describe('Book Management Delete Component', () => {
        let comp: BookDeleteDialogComponent;
        let fixture: ComponentFixture<BookDeleteDialogComponent>;
        let service: BookService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [BookDeleteDialogComponent]
            })
                .overrideTemplate(BookDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
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
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
