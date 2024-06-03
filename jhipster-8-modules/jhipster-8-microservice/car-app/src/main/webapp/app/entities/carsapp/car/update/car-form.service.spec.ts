import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../car.test-samples';

import { CarFormService } from './car-form.service';

describe('Car Form Service', () => {
  let service: CarFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarFormService);
  });

  describe('Service methods', () => {
    describe('createCarFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });

      it('passing ICar should create a new form with FormGroup', () => {
        const formGroup = service.createCarFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });
    });

    describe('getCar', () => {
      it('should return NewCar for default Car initial value', () => {
        const formGroup = service.createCarFormGroup(sampleWithNewData);

        const car = service.getCar(formGroup) as any;

        expect(car).toMatchObject(sampleWithNewData);
      });

      it('should return NewCar for empty Car initial value', () => {
        const formGroup = service.createCarFormGroup();

        const car = service.getCar(formGroup) as any;

        expect(car).toMatchObject({});
      });

      it('should return ICar', () => {
        const formGroup = service.createCarFormGroup(sampleWithRequiredData);

        const car = service.getCar(formGroup) as any;

        expect(car).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICar should not enable id FormControl', () => {
        const formGroup = service.createCarFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCar should disable id FormControl', () => {
        const formGroup = service.createCarFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
