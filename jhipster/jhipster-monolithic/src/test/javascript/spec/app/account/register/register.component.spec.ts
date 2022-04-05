import { ComponentFixture, TestBed, async, inject, tick, fakeAsync } from '@angular/core/testing';
import { Renderer, ElementRef } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { BaeldungTestModule } from '../../../test.module';
import { LoginModalService } from '../../../../../../main/webapp/app/shared';
import { Register } from '../../../../../../main/webapp/app/account/register/register.service';
import { RegisterComponent } from '../../../../../../main/webapp/app/account/register/register.component';


describe('Component Tests', () => {

    describe('RegisterComponent', () => {
        let fixture: ComponentFixture<RegisterComponent>;
        let comp: RegisterComponent;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BaeldungTestModule],
                declarations: [RegisterComponent],
                providers: [
                    Register,
                    {
                        provide: LoginModalService,
                        useValue: null
                    },
                    {
                        provide: Renderer,
                        useValue: null
                    },
                    {
                        provide: ElementRef,
                        useValue: null
                    }
                ]
            }).overrideComponent(RegisterComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegisterComponent);
            comp = fixture.componentInstance;
            comp.ngOnInit();
        });

        it('should ensure the two passwords entered match', function () {
            comp.registerAccount.password = 'password';
            comp.confirmPassword = 'non-matching';

            comp.register();

            expect(comp.doNotMatch).toEqual('ERROR');
        });

        it('should update success to OK after creating an account',
            inject([Register, JhiLanguageService],
                fakeAsync((service: Register, mockTranslate: MockLanguageService) => {
                    spyOn(service, 'save').and.returnValue(Observable.of({}));
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(service.save).toHaveBeenCalledWith({
                        password: 'password',
                        langKey: 'en'
                    });
                    expect(comp.success).toEqual(true);
                    expect(comp.registerAccount.langKey).toEqual('en');
                    expect(mockTranslate.getCurrentSpy).toHaveBeenCalled();
                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it('should notify of user existence upon 400/login already in use',
            inject([Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(Observable.throw({
                        status: 400,
                        _body: 'login already in use'
                    }));
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorUserExists).toEqual('ERROR');
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it('should notify of email existence upon 400/e-mail address already in use',
            inject([Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(Observable.throw({
                        status: 400,
                        _body: 'e-mail address already in use'
                    }));
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorEmailExists).toEqual('ERROR');
                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it('should notify of generic error',
            inject([Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(Observable.throw({
                        status: 503
                    }));
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toEqual('ERROR');
                })
            )
        );
    });
});
