import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { PasswordService } from './password.service';

describe('Password Service', () => {
  let service: PasswordService;
  let httpMock: HttpTestingController;
  let applicationConfigService: ApplicationConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    service = TestBed.inject(PasswordService);
    applicationConfigService = TestBed.inject(ApplicationConfigService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('Service methods', () => {
    it('should call change-password endpoint with correct values', () => {
      // GIVEN
      const password1 = 'password1';
      const password2 = 'password2';

      // WHEN
      service.save(password2, password1).subscribe();

      const testRequest = httpMock.expectOne({
        method: 'POST',
        url: applicationConfigService.getEndpointFor('api/account/change-password'),
      });

      // THEN
      expect(testRequest.request.body).toEqual({ currentPassword: password1, newPassword: password2 });
    });
  });
});
