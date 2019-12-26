/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { BookUpdateComponent } from 'app/entities/book/book-update.component';
import { BookService } from 'app/entities/book/book.service';
import { Book } from 'app/shared/model/book.model';

describe('Component Tests', () => {
    describe('Book Management Update Component', () => {
        let comp: BookUpdateComponent;
        let fixture: ComponentFixture<BookUpdateComponent>;
        let service: BookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [BookUpdateComponent]
            })
                .overrideTemplate(BookUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Book(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.book = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Book();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.book = entity;
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
