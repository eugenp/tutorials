import { SpyObject } from './spyobject';
import { JhiLanguageService } from 'ng-jhipster';
import Spy = jasmine.Spy;

export class MockLanguageService extends SpyObject {

    getCurrentSpy: Spy;
    fakeResponse: any;

    constructor() {
        super(JhiLanguageService);

        this.fakeResponse = 'en';
        this.getCurrentSpy = this.spy('getCurrent').andReturn(Promise.resolve(this.fakeResponse));
    }

    init() {}

    changeLanguage(languageKey: string) {}

    setLocations(locations: string[]) {}

    addLocation(location: string) {}

    reload() {}
}
