import { ComponentFixture, TestBed, inject, tick, fakeAsync } from '@angular/core/testing';
import { Observable, of, throwError } from 'rxjs';
import { Renderer, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { BookstoreTestModule } from '../../../../test.module';
import { PasswordResetFinishComponent } from 'app/account/password-reset/finish/password-reset-finish.component';
import { PasswordResetFinishService } from 'app/account/password-reset/finish/password-reset-finish.service';
import { MockActivatedRoute } from '../../../../helpers/mock-route.service';

describe('Component Tests', () => {
    describe('PasswordResetFinishComponent', () => {
        let fixture: ComponentFixture<PasswordResetFinishComponent>;
        let comp: PasswordResetFinishComponent;

        beforeEach(() => {
            fixture = TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [PasswordResetFinishComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({ key: 'XYZPDQ' })
                    },
                    {
                        provide: Renderer,
                        useValue: {
                            invokeElementMethod(renderElement: any, methodName: string, args?: any[]) {}
                        }
                    },
                    {
                        provide: ElementRef,
                        useValue: new ElementRef(null)
                    }
                ]
            })
                .overrideTemplate(PasswordResetFinishComponent, '')
                .createComponent(PasswordResetFinishComponent);
        });

        beforeEach(() => {
            fixture = TestBed.createComponent(PasswordResetFinishComponent);
            comp = fixture.componentInstance;
            comp.ngOnInit();
        });

        it('should define its initial state', () => {
            comp.ngOnInit();

            expect(comp.keyMissing).toBeFalsy();
            expect(comp.key).toEqual('XYZPDQ');
            expect(comp.resetAccount).toEqual({});
        });

        it('sets focus after the view has been initialized', inject([ElementRef], (elementRef: ElementRef) => {
            const element = fixture.nativeElement;
            const node = {
                focus() {}
            };

            elementRef.nativeElement = element;
            spyOn(element, 'querySelector').and.returnValue(node);
            spyOn(node, 'focus');

            comp.ngAfterViewInit();

            expect(element.querySelector).toHaveBeenCalledWith('#password');
            expect(node.focus).toHaveBeenCalled();
        }));

        it('should ensure the two passwords entered match', () => {
            comp.resetAccount.password = 'password';
            comp.confirmPassword = 'non-matching';

            comp.finishReset();

            expect(comp.doNotMatch).toEqual('ERROR');
        });

        it('should update success to OK after resetting password', inject(
            [PasswordResetFinishService],
            fakeAsync((service: PasswordResetFinishService) => {
                spyOn(service, 'save').and.returnValue(of({}));

                comp.resetAccount.password = 'password';
                comp.confirmPassword = 'password';

                comp.finishReset();
                tick();

                expect(service.save).toHaveBeenCalledWith({
                    key: 'XYZPDQ',
                    newPassword: 'password'
                });
                expect(comp.success).toEqual('OK');
            })
        ));

        it('should notify of generic error', inject(
            [PasswordResetFinishService],
            fakeAsync((service: PasswordResetFinishService) => {
                spyOn(service, 'save').and.returnValue(throwError('ERROR'));

                comp.resetAccount.password = 'password';
                comp.confirmPassword = 'password';

                comp.finishReset();
                tick();

                expect(service.save).toHaveBeenCalledWith({
                    key: 'XYZPDQ',
                    newPassword: 'password'
                });
                expect(comp.success).toBeNull();
                expect(comp.error).toEqual('ERROR');
            })
        ));
    });
});
