import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { SERVER_API_URL } from 'app/app.constants';
import { AccountService } from 'app/core';
import { JhiDateUtils } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

describe('Service Tests', () => {
    describe('Account Service', () => {
        let service: AccountService;
        let httpMock;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule],
                providers: [JhiDateUtils, SessionStorageService]
            });

            service = TestBed.get(AccountService);
            httpMock = TestBed.get(HttpTestingController);
        });

        afterEach(() => {
            httpMock.verify();
        });

        describe('Service methods', () => {
            it('should call /account if user is undefined', () => {
                service.identity().then(() => {});
                const req = httpMock.expectOne({ method: 'GET' });
                const resourceUrl = SERVER_API_URL + 'api/account';

                expect(req.request.url).toEqual(`${resourceUrl}`);
            });

            it('should call /account only once', () => {
                service.identity().then(() => service.identity().then(() => {}));
                const req = httpMock.expectOne({ method: 'GET' });
                const resourceUrl = SERVER_API_URL + 'api/account';

                expect(req.request.url).toEqual(`${resourceUrl}`);
                req.flush({
                    firstName: 'John'
                });
            });

            describe('hasAuthority', () => {
                it('should return false if user is not logged', async () => {
                    const hasAuthority = await service.hasAuthority('ROLE_USER');
                    expect(hasAuthority).toBeFalsy();
                });

                it('should return false if user is logged and has not authority', async () => {
                    service.authenticate({
                        authorities: ['ROLE_USER']
                    });

                    const hasAuthority = await service.hasAuthority('ROLE_ADMIN');

                    expect(hasAuthority).toBeFalsy();
                });

                it('should return true if user is logged and has authority', async () => {
                    service.authenticate({
                        authorities: ['ROLE_USER']
                    });

                    const hasAuthority = await service.hasAuthority('ROLE_USER');

                    expect(hasAuthority).toBeTruthy();
                });
            });

            describe('hasAnyAuthority', () => {
                it('should return false if user is not logged', async () => {
                    const hasAuthority = await service.hasAnyAuthority(['ROLE_USER']);
                    expect(hasAuthority).toBeFalsy();
                });

                it('should return false if user is logged and has not authority', async () => {
                    service.authenticate({
                        authorities: ['ROLE_USER']
                    });

                    const hasAuthority = await service.hasAnyAuthority(['ROLE_ADMIN']);

                    expect(hasAuthority).toBeFalsy();
                });

                it('should return true if user is logged and has authority', async () => {
                    service.authenticate({
                        authorities: ['ROLE_USER']
                    });

                    const hasAuthority = await service.hasAnyAuthority(['ROLE_USER', 'ROLE_ADMIN']);

                    expect(hasAuthority).toBeTruthy();
                });
            });
        });
    });
});
