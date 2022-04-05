import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBook } from 'app/shared/model/book.model';
import { AccountService } from 'app/core';
import { BookService } from './book.service';

@Component({
    selector: 'jhi-book',
    templateUrl: './book.component.html'
})
export class BookComponent implements OnInit, OnDestroy {
    books: IBook[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bookService: BookService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bookService
            .query()
            .pipe(
                filter((res: HttpResponse<IBook[]>) => res.ok),
                map((res: HttpResponse<IBook[]>) => res.body)
            )
            .subscribe(
                (res: IBook[]) => {
                    this.books = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBook) {
        return item.id;
    }

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe('bookListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
