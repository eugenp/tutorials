/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BookService } from 'app/entities/book/book.service';
import { IBook, Book } from 'app/shared/model/book.model';

describe('Service Tests', () => {
    describe('Book Service', () => {
        let injector: TestBed;
        let service: BookService;
        let httpMock: HttpTestingController;
        let elemDefault: IBook;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BookService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Book(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        published: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Book', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        published: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        published: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Book(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Book', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        author: 'BBBBBB',
                        published: currentDate.format(DATE_FORMAT),
                        quantity: 1,
                        price: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        published: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Book', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        author: 'BBBBBB',
                        published: currentDate.format(DATE_FORMAT),
                        quantity: 1,
                        price: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        published: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Book', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
