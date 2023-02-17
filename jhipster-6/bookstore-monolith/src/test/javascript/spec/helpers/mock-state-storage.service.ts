import { SpyObject } from './spyobject';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import Spy = jasmine.Spy;

export class MockStateStorageService extends SpyObject {
    getUrlSpy: Spy;
    storeUrlSpy: Spy;

    constructor() {
        super(StateStorageService);
        this.setUrlSpy({});
        this.storeUrlSpy = this.spy('storeUrl').andReturn(this);
    }

    setUrlSpy(json) {
        this.getUrlSpy = this.spy('getUrl').andReturn(json);
    }

    setResponse(json: any): void {
        this.setUrlSpy(json);
    }
}
