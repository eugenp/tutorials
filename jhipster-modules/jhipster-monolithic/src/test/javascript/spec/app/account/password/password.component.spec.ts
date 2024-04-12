import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { BaeldungTestModule } from '../../../test.module';
import { PasswordComponent } from '../../../../../../main/webapp/app/account/password/password.component';
import { Password } from '../../../../../../main/webapp/app/account/password/password.service';
import { Principal } from '../../../../../../main/webapp/app/shared/auth/principal.service';
import { AccountService } from '../../../../../../main/webapp/app/shared/auth/account.service';


describe('Component Tests', () => {

    describe('PasswordComponent', () => {

        let comp: PasswordComponent;
        let fixture: ComponentFixture<PasswordComponent>;
        let service: Password;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BaeldungTestModule],
                declarations: [PasswordComponent],
                providers: [
                    Principal,
                    AccountService,
                    Password
                ]
            }).overrideComponent(PasswordComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PasswordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Password);
        });

        it('should show error if passwords do not match', () => {
            // GIVEN
            comp.password = 'password1';
            comp.confirmPassword = 'password2';
            // WHEN
            comp.changePassword();
            // THEN
            expect(comp.doNotMatch).toBe('ERROR');
            expect(comp.error).toBeNull();
            expect(comp.success).toBeNull();
        });

        it('should call Auth.changePassword when passwords match', () => {
            // GIVEN
            spyOn(service, 'save').and.returnValue(Observable.of(true));
            comp.password = comp.confirmPassword = 'myPassword';

            // WHEN
            comp.changePassword();

            // THEN
            expect(service.save).toHaveBeenCalledWith('myPassword');
        });

        it('should set success to OK upon success', function() {
            // GIVEN
            spyOn(service, 'save').and.returnValue(Observable.of(true));
            comp.password = comp.confirmPassword = 'myPassword';

            // WHEN
            comp.changePassword();

            // THEN
            expect(comp.doNotMatch).toBeNull();
            expect(comp.error).toBeNull();
            expect(comp.success).toBe('OK');
        });

        it('should notify of error if change password fails', function() {
            // GIVEN
            spyOn(service, 'save').and.returnValue(Observable.throw('ERROR'));
            comp.password = comp.confirmPassword = 'myPassword';

            // WHEN
            comp.changePassword();

            // THEN
            expect(comp.doNotMatch).toBeNull();
            expect(comp.success).toBeNull();
            expect(comp.error).toBe('ERROR');
        });
    });
});
