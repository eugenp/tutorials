import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICar } from '../car.model';
import { CarService } from '../service/car.service';

const carResolve = (route: ActivatedRouteSnapshot): Observable<null | ICar> => {
  const id = route.params['id'];
  if (id) {
    return inject(CarService)
      .find(id)
      .pipe(
        mergeMap((car: HttpResponse<ICar>) => {
          if (car.body) {
            return of(car.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default carResolve;
