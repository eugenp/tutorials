import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable, throwError } from 'rxjs';

import { GatewayTestModule } from '../../../test.module';
import { Principal, AccountService } from 'app/core';
import { SettingsComponent } from 'app/account/settings/settings.component';

describe('Component Tests', () => {
    describe('SettingsComponent', () => {
        let comp: SettingsComponent;
        let fixture: ComponentFixture<SettingsComponent>;
        let mockAuth: any;
        let mockPrincipal: any;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [GatewayTestModule],
                    declarations: [SettingsComponent],
                    providers: []
                })
                    .overrideTemplate(SettingsComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(SettingsComponent);
            comp = fixture.componentInstance;
            mockAuth = fixture.debugElement.injector.get(AccountService);
            mockPrincipal = fixture.debugElement.injector.get(Principal);
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
            mockPrincipal.setResponse(accountValues);

            // WHEN
            comp.settingsAccount = accountValues;
            comp.save();

            // THEN
            expect(mockPrincipal.identitySpy).toHaveBeenCalled();
            expect(mockAuth.saveSpy).toHaveBeenCalledWith(accountValues);
            expect(comp.settingsAccount).toEqual(accountValues);
        });

        it('should notify of success upon successful save', () => {
            // GIVEN
            const accountValues = {
                firstName: 'John',
                lastName: 'Doe'
            };
            mockPrincipal.setResponse(accountValues);

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
