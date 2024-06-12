import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICar } from '../car.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../car.test-samples';

import { CarService } from './car.service';

const requireRestSample: ICar = {
  ...sampleWithRequiredData,
};

describe('Car Service', () => {
  let service: CarService;
  let httpMock: HttpTestingController;
  let expectedResult: ICar | ICar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Car', () => {
      const car = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(car).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Car', () => {
      const car = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(car).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Car', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Car', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Car', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarToCollectionIfMissing', () => {
      it('should add a Car to an empty array', () => {
        const car: ICar = sampleWithRequiredData;
        expectedResult = service.addCarToCollectionIfMissing([], car);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(car);
      });

      it('should not add a Car to an array that contains it', () => {
        const car: ICar = sampleWithRequiredData;
        const carCollection: ICar[] = [
          {
            ...car,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarToCollectionIfMissing(carCollection, car);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Car to an array that doesn't contain it", () => {
        const car: ICar = sampleWithRequiredData;
        const carCollection: ICar[] = [sampleWithPartialData];
        expectedResult = service.addCarToCollectionIfMissing(carCollection, car);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(car);
      });

      it('should add only unique Car to an array', () => {
        const carArray: ICar[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carCollection: ICar[] = [sampleWithRequiredData];
        expectedResult = service.addCarToCollectionIfMissing(carCollection, ...carArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const car: ICar = sampleWithRequiredData;
        const car2: ICar = sampleWithPartialData;
        expectedResult = service.addCarToCollectionIfMissing([], car, car2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(car);
        expect(expectedResult).toContain(car2);
      });

      it('should accept null and undefined values', () => {
        const car: ICar = sampleWithRequiredData;
        expectedResult = service.addCarToCollectionIfMissing([], null, car, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(car);
      });

      it('should return initial array if no Car is added', () => {
        const carCollection: ICar[] = [sampleWithRequiredData];
        expectedResult = service.addCarToCollectionIfMissing(carCollection, undefined, null);
        expect(expectedResult).toEqual(carCollection);
      });
    });

    describe('compareCar', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCar(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCar(entity1, entity2);
        const compareResult2 = service.compareCar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCar(entity1, entity2);
        const compareResult2 = service.compareCar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCar(entity1, entity2);
        const compareResult2 = service.compareCar(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
