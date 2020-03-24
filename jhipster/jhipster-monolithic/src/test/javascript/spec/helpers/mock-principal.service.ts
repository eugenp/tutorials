import { SpyObject } from './spyobject';
import { Principal } from '../../../../main/webapp/app/shared/auth/principal.service';
import Spy = jasmine.Spy;

export class MockPrincipal extends SpyObject {

    identitySpy: Spy;
    fakeResponse: any;

    constructor() {
        super(Principal);

        this.fakeResponse = {};
        this.identitySpy = this.spy('identity').andReturn(Promise.resolve(this.fakeResponse));
    }

    setResponse(json: any): void {
        this.fakeResponse = json;
    }
}
