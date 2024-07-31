import { SpyObject } from './spyobject';
import { AccountService } from 'app/core/auth/account.service';
import Spy = jasmine.Spy;

export class MockAccountService extends SpyObject {
    getSpy: Spy;
    saveSpy: Spy;
    fakeResponse: any;

    constructor() {
        super(AccountService);

        this.fakeResponse = null;
        this.getSpy = this.spy('get').andReturn(this);
        this.saveSpy = this.spy('save').andReturn(this);
    }

    subscribe(callback: any) {
        callback(this.fakeResponse);
    }

    setResponse(json: any): void {
        this.fakeResponse = json;
    }
}
