import { SpyObject } from './spyobject';
import { JhiEventManager } from 'ng-jhipster';
import Spy = jasmine.Spy;

export class MockEventManager extends SpyObject {
    broadcastSpy: Spy;

    constructor() {
        super(JhiEventManager);
        this.broadcastSpy = this.spy('broadcast').andReturn(this);
    }
}
