import { SpyObject } from './spyobject';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import Spy = jasmine.Spy;

export class MockActiveModal extends SpyObject {
    dismissSpy: Spy;

    constructor() {
        super(NgbActiveModal);
        this.dismissSpy = this.spy('dismiss').andReturn(this);
    }
}
