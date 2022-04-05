import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable, throwError } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { AccountService } from 'app/core';
import { SettingsComponent } from 'app/account/settings/settings.component';

describe('Component Tests', () => {
    describe('SettingsComponent', () => {
        let comp: SettingsComponent;
        let fixture: ComponentFixture<SettingsComponent>;
        let mockAuth: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [SettingsComponent],
                providers: []
            })
                .overrideTemplate(SettingsComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SettingsComponent);
            comp = fixture.componentInstance;
            mockAuth = fixture.debugElement.injector.get(AccountService);
        });

        it('should send the current identity upon save', () => {
            // GIVEN
            const accountValues = {
                firstName: 'John',
                lastName: 'Doe',

                activated: true,
                email: 'john.doe@mail.com',
                langKey: 'en',
                login: 'john'
            };
            mockAuth.setIdentityResponse(accountValues);

            // WHEN
            comp.settingsAccount = accountValues;
            comp.save();

            // THEN
            expect(mockAuth.identitySpy).toHaveBeenCalled();
            expect(mockAuth.saveSpy).toHaveBeenCalledWith(accountValues);
            expect(comp.settingsAccount).toEqual(accountValues);
        });

        it('should notify of success upon successful save', () => {
            // GIVEN
            const accountValues = {
                firstName: 'John',
                lastName: 'Doe'
            };
            mockAuth.setIdentityResponse(accountValues);

            // WHEN
            comp.save();

            // THEN
            expect(comp.error).toBeNull();
            expect(comp.success).toBe('OK');
        });

        it('should notify of error upon failed save', () => {
            // GIVEN
            mockAuth.saveSpy.and.returnValue(throwError('ERROR'));

            // WHEN
            comp.save();

            // THEN
            expect(comp.error).toEqual('ERROR');
            expect(comp.success).toBeNull();
        });
    });
});
