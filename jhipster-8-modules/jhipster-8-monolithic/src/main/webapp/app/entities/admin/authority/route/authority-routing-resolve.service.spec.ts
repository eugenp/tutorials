import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAuthority } from '../authority.model';
import { AuthorityService } from '../service/authority.service';

import authorityResolve from './authority-routing-resolve.service';

describe('Authority routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AuthorityService;
  let resultAuthority: IAuthority | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(AuthorityService);
    resultAuthority = undefined;
  });

  describe('resolve', () => {
    it('should return IAuthority returned by find', () => {
      // GIVEN
      service.find = jest.fn(name => of(new HttpResponse({ body: { name } })));
      mockActivatedRouteSnapshot.params = { name: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        authorityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthority = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultAuthority).toEqual({ name: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        authorityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthority = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAuthority).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAuthority>({ body: null })));
      mockActivatedRouteSnapshot.params = { name: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        authorityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthority = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultAuthority).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
