import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuote } from 'app/shared/model/quotes/quote.model';

@Component({
    selector: 'jhi-quote-detail',
    templateUrl: './quote-detail.component.html'
})
export class QuoteDetailComponent implements OnInit {
    quote: IQuote;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quote }) => {
            this.quote = quote;
        });
    }

    previousState() {
        window.history.back();
    }
}
