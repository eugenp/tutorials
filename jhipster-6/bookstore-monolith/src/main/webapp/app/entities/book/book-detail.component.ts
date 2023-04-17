import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-book-detail',
    templateUrl: './book-detail.component.html'
})
export class BookDetailComponent implements OnInit {
    book: IBook;

    constructor(protected activatedRoute: ActivatedRoute, protected bookService: BookService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ book }) => {
            this.book = book;
        });
    }

    previousState() {
        window.history.back();
    }

    purchase(id: number) {
        console.log('Purchasing book ' + id);
        this.bookService.purchase(id).subscribe(
            (res: HttpResponse<IBook>) => {
                this.book = res.body;
            },
            (res: HttpErrorResponse) => console.log(res.message)
        );
    }
}
