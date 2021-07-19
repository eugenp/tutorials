/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookstoreTestModule } from '../../../test.module';
import { BookComponent } from 'app/entities/book/book.component';
import { BookService } from 'app/entities/book/book.service';
import { Book } from 'app/shared/model/book.model';

describe('Component Tests', () => {
    describe('Book Management Component', () => {
        let comp: BookComponent;
        let fixture: ComponentFixture<BookComponent>;
        let service: BookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [BookComponent],
                providers: []
            })
                .overrideTemplate(BookComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Book(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.books[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
