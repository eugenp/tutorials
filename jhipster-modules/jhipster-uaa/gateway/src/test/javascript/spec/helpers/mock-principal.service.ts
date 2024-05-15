import { SpyObject } from './spyobject';
import { Principal } from 'app/core/auth/principal.service';
import Spy = jasmine.Spy;

export class MockPrincipal extends SpyObject {
    identitySpy: Spy;

    constructor() {
        super(Principal);

        this.setIdentitySpy({});
    }
    setIdentitySpy(json: any): any {
        this.identitySpy = this.spy('identity').andReturn(Promise.resolve(json));
    }

    setResponse(json: any): void {
        this.setIdentitySpy(json);
    }
}
