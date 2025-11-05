import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../authority.test-samples';

import { AuthorityFormService } from './authority-form.service';

describe('Authority Form Service', () => {
  let service: AuthorityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthorityFormService);
  });

  describe('Service methods', () => {
    describe('createAuthorityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuthorityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            name: expect.any(Object),
          }),
        );
      });

      it('passing IAuthority should create a new form with FormGroup', () => {
        const formGroup = service.createAuthorityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getAuthority', () => {
      it('should return NewAuthority for default Authority initial value', () => {
        const formGroup = service.createAuthorityFormGroup(sampleWithNewData);

        const authority = service.getAuthority(formGroup) as any;

        expect(authority).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuthority for empty Authority initial value', () => {
        const formGroup = service.createAuthorityFormGroup();

        const authority = service.getAuthority(formGroup) as any;

        expect(authority).toMatchObject({});
      });

      it('should return IAuthority', () => {
        const formGroup = service.createAuthorityFormGroup(sampleWithRequiredData);

        const authority = service.getAuthority(formGroup) as any;

        expect(authority).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});
