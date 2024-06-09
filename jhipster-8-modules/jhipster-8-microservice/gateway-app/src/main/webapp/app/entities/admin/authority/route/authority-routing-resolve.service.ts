import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuthority } from '../authority.model';
import { AuthorityService } from '../service/authority.service';

const authorityResolve = (route: ActivatedRouteSnapshot): Observable<null | IAuthority> => {
  const id = route.params['name'];
  if (id) {
    return inject(AuthorityService)
      .find(id)
      .pipe(
        mergeMap((authority: HttpResponse<IAuthority>) => {
          if (authority.body) {
            return of(authority.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default authorityResolve;
