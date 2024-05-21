import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';

import UserManagementDetailComponent from './user-management-detail.component';

describe('User Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserManagementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: UserManagementDetailComponent,
              resolve: {
                user: () =>
                  of({
                    id: 123,
                    login: 'user',
                    firstName: 'first',
                    lastName: 'last',
                    email: 'first@last.com',
                    activated: true,
                    langKey: 'en',
                    authorities: [Authority.USER],
                    createdBy: 'admin',
                  }),
              },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UserManagementDetailComponent, '')
      .compileComponents();
  });

  describe('Construct', () => {
    it('Should call load all on construct', async () => {
      // WHEN
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UserManagementDetailComponent);

      // THEN
      expect(instance.user()).toEqual(
        expect.objectContaining({
          id: 123,
          login: 'user',
          firstName: 'first',
          lastName: 'last',
          email: 'first@last.com',
          activated: true,
          langKey: 'en',
          authorities: [Authority.USER],
          createdBy: 'admin',
        }),
      );
    });
  });
});
