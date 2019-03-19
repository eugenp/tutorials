import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBook } from 'app/shared/model/book.model';

@Component({
    selector: 'jhi-book-detail',
    templateUrl: './book-detail.component.html'
})
export class BookDetailComponent implements OnInit {
    book: IBook;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ book }) => {
            this.book = book;
        });
    }

    previousState() {
        window.history.back();
    }
}
