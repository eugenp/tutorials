import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { HealthService } from './health.service';

describe('HealthService Service', () => {
  let service: HealthService;
  let httpMock: HttpTestingController;
  let applicationConfigService: ApplicationConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    service = TestBed.inject(HealthService);
    applicationConfigService = TestBed.inject(ApplicationConfigService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('Service methods', () => {
    it('should call management/health endpoint with correct values', () => {
      // GIVEN
      let expectedResult;
      const checkHealth = {
        components: [],
      };

      // WHEN
      service.checkHealth().subscribe(received => {
        expectedResult = received;
      });
      const testRequest = httpMock.expectOne({
        method: 'GET',
        url: applicationConfigService.getEndpointFor('management/health'),
      });
      testRequest.flush(checkHealth);

      // THEN
      expect(expectedResult).toEqual(checkHealth);
    });
  });
});
