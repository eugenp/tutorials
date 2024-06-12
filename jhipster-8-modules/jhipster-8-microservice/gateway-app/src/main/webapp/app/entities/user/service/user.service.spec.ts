import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUser } from '../user.model';
import { sampleWithRequiredData, sampleWithPartialData, sampleWithFullData } from '../user.test-samples';

import { UserService } from './user.service';

const requireRestSample: IUser = {
  ...sampleWithRequiredData,
};

describe('User Service', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  let expectedResult: IUser | IUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserService);
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

    it('should return a list of User', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addUserToCollectionIfMissing', () => {
      it('should add a User to an empty array', () => {
        const user: IUser = sampleWithRequiredData;
        expectedResult = service.addUserToCollectionIfMissing([], user);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user);
      });

      it('should not add a User to an array that contains it', () => {
        const user: IUser = sampleWithRequiredData;
        const userCollection: IUser[] = [
          {
            ...user,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserToCollectionIfMissing(userCollection, user);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a User to an array that doesn't contain it", () => {
        const user: IUser = sampleWithRequiredData;
        const userCollection: IUser[] = [sampleWithPartialData];
        expectedResult = service.addUserToCollectionIfMissing(userCollection, user);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user);
      });

      it('should add only unique User to an array', () => {
        const userArray: IUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userCollection: IUser[] = [sampleWithRequiredData];
        expectedResult = service.addUserToCollectionIfMissing(userCollection, ...userArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const user: IUser = sampleWithRequiredData;
        const user2: IUser = sampleWithPartialData;
        expectedResult = service.addUserToCollectionIfMissing([], user, user2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(user);
        expect(expectedResult).toContain(user2);
      });

      it('should accept null and undefined values', () => {
        const user: IUser = sampleWithRequiredData;
        expectedResult = service.addUserToCollectionIfMissing([], null, user, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(user);
      });

      it('should return initial array if no User is added', () => {
        const userCollection: IUser[] = [sampleWithRequiredData];
        expectedResult = service.addUserToCollectionIfMissing(userCollection, undefined, null);
        expect(expectedResult).toEqual(userCollection);
      });
    });

    describe('compareUser', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUser(entity1, entity2);
        const compareResult2 = service.compareUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUser(entity1, entity2);
        const compareResult2 = service.compareUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUser(entity1, entity2);
        const compareResult2 = service.compareUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
