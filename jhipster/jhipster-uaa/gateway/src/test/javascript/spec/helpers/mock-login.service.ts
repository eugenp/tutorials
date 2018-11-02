import { SpyObject } from './spyobject';
import { LoginService } from 'app/core/login/login.service';
import Spy = jasmine.Spy;

export class MockLoginService extends SpyObject {
    loginSpy: Spy;
    logoutSpy: Spy;
    registerSpy: Spy;
    requestResetPasswordSpy: Spy;
    cancelSpy: Spy;

    constructor() {
        super(LoginService);

        this.setLoginSpy({});
        this.logoutSpy = this.spy('logout').andReturn(this);
        this.registerSpy = this.spy('register').andReturn(this);
        this.requestResetPasswordSpy = this.spy('requestResetPassword').andReturn(this);
        this.cancelSpy = this.spy('cancel').andReturn(this);
    }

    setLoginSpy(json: any) {
        this.loginSpy = this.spy('login').andReturn(Promise.resolve(json));
    }

    setResponse(json: any): void {
        this.setLoginSpy(json);
    }
}
