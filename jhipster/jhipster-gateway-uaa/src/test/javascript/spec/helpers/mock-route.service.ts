import { ActivatedRoute, Router } from '@angular/router';
import { SpyObject } from './spyobject';
import { Observable, of } from 'rxjs';
import Spy = jasmine.Spy;

export class MockActivatedRoute extends ActivatedRoute {
    constructor(parameters?: any) {
        super();
        this.queryParams = of(parameters);
        this.params = of(parameters);
        this.data = of({
            ...parameters,
            pagingParams: {
                page: 10,
                ascending: false,
                predicate: 'id'
            }
        });
    }
}

export class MockRouter extends SpyObject {
    navigateSpy: Spy;

    constructor() {
        super(Router);
        this.navigateSpy = this.spy('navigate');
    }
}
