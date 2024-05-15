import { TestBed } from '@angular/core/testing';
import { JhiDateUtils } from 'ng-jhipster';

import { UserService, User } from 'app/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('Service Tests', () => {
    describe('User Service', () => {
        let service: UserService;
        let httpMock;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule],
                providers: [JhiDateUtils]
            });

            service = TestBed.get(UserService);
            httpMock = TestBed.get(HttpTestingController);
        });

        afterEach(() => {
            httpMock.verify();
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find('user').subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });
                const resourceUrl = SERVER_API_URL + 'uaa/api/users';
                expect(req.request.url).toEqual(`${resourceUrl}/user`);
            });
            it('should return User', () => {
                service.find('user').subscribe(received => {
                    expect(received.body.login).toEqual('user');
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(new User(1, 'user'));
            });

            it('should return Authorities', () => {
                service.authorities().subscribe(_authorities => {
                    expect(_authorities).toEqual(['ROLE_USER', 'ROLE_ADMIN']);
                });
                const req = httpMock.expectOne({ method: 'GET' });

                req.flush(['ROLE_USER', 'ROLE_ADMIN']);
            });

            it('should propagate not found response', () => {
                service.find('user').subscribe(null, (_error: any) => {
                    expect(_error.status).toEqual(404);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush('Invalid request parameters', {
                    status: 404,
                    statusText: 'Bad Request'
                });
            });
        });
    });
});
