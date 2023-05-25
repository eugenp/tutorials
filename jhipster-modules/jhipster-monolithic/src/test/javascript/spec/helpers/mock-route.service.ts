import { ActivatedRoute, Params } from '@angular/router';
import { Observable } from 'rxjs';

export class MockActivatedRoute extends ActivatedRoute {

    constructor(parameters?: any) {
        super();
        this.queryParams = Observable.of(parameters);
        this.params = Observable.of(parameters);
    }
}

export class MockRouter {
    navigate = jasmine.createSpy('navigate');
}
