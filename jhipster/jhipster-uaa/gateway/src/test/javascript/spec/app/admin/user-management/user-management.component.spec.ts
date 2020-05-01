import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { UserMgmtComponent } from 'app/admin/user-management/user-management.component';
import { UserService, User } from 'app/core';

describe('Component Tests', () => {
    describe('User Management Component', () => {
        let comp: UserMgmtComponent;
        let fixture: ComponentFixture<UserMgmtComponent>;
        let service: UserService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [GatewayTestModule],
                    declarations: [UserMgmtComponent]
                })
                    .overrideTemplate(UserMgmtComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserService);
        });

        describe('OnInit', () => {
            it(
                'Should call load all on init',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const headers = new HttpHeaders().append('link', 'link;link');
                        spyOn(service, 'query').and.returnValue(
                            of(
                                new HttpResponse({
                                    body: [new User(123)],
                                    headers
                                })
                            )
                        );

                        // WHEN
                        comp.ngOnInit();
                        tick(); // simulate async

                        // THEN
                        expect(service.query).toHaveBeenCalled();
                        expect(comp.users[0]).toEqual(jasmine.objectContaining({ id: 123 }));
                    })
                )
            );
        });

        describe('setActive', () => {
            it(
                'Should update user and call load all',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const headers = new HttpHeaders().append('link', 'link;link');
                        const user = new User(123);
                        spyOn(service, 'query').and.returnValue(
                            of(
                                new HttpResponse({
                                    body: [user],
                                    headers
                                })
                            )
                        );
                        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ status: 200 })));

                        // WHEN
                        comp.setActive(user, true);
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(user);
                        expect(service.query).toHaveBeenCalled();
                        expect(comp.users[0]).toEqual(jasmine.objectContaining({ id: 123 }));
                    })
                )
            );
        });
    });
});
