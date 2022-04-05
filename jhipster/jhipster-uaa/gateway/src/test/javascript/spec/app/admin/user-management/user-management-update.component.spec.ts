import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { UserMgmtUpdateComponent } from 'app/admin/user-management/user-management-update.component';
import { UserService, User, JhiLanguageHelper } from 'app/core';

describe('Component Tests', () => {
    describe('User Management Update Component', () => {
        let comp: UserMgmtUpdateComponent;
        let fixture: ComponentFixture<UserMgmtUpdateComponent>;
        let service: UserService;
        let mockLanguageHelper: any;
        const route = ({
            data: of({ user: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', ['ROLE_USER'], 'admin', null, null, null) })
        } as any) as ActivatedRoute;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [GatewayTestModule],
                    declarations: [UserMgmtUpdateComponent],
                    providers: [
                        {
                            provide: ActivatedRoute,
                            useValue: route
                        }
                    ]
                })
                    .overrideTemplate(UserMgmtUpdateComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserService);
            mockLanguageHelper = fixture.debugElement.injector.get(JhiLanguageHelper);
        });

        describe('OnInit', () => {
            it(
                'Should load authorities and language on init',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'authorities').and.returnValue(of(['USER']));

                        // WHEN
                        comp.ngOnInit();

                        // THEN
                        expect(service.authorities).toHaveBeenCalled();
                        expect(comp.authorities).toEqual(['USER']);
                        expect(mockLanguageHelper.getAllSpy).toHaveBeenCalled();
                    })
                )
            );
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing user',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new User(123);
                        spyOn(service, 'update').and.returnValue(
                            of(
                                new HttpResponse({
                                    body: entity
                                })
                            )
                        );
                        comp.user = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                    })
                )
            );

            it(
                'Should call create service on save for new user',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new User();
                        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                        comp.user = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                    })
                )
            );
        });
    });
});
