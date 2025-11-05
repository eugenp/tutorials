import { TestBed } from '@angular/core/testing';

import { ApplicationConfigService } from './application-config.service';

describe('ApplicationConfigService', () => {
  let service: ApplicationConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApplicationConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('without prefix', () => {
    it('should return correctly', () => {
      expect(service.getEndpointFor('api')).toEqual('api');
    });

    it('should return correctly when passing microservice', () => {
      expect(service.getEndpointFor('api', 'microservice')).toEqual('services/microservice/api');
    });
  });

  describe('with prefix', () => {
    beforeEach(() => {
      service.setEndpointPrefix('prefix/');
    });

    it('should return correctly', () => {
      expect(service.getEndpointFor('api')).toEqual('prefix/api');
    });

    it('should return correctly when passing microservice', () => {
      expect(service.getEndpointFor('api', 'microservice')).toEqual('prefix/services/microservice/api');
    });
  });
});
