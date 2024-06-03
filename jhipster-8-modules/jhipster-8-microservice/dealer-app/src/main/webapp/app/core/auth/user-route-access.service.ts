import { inject, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { map } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { StateStorageService } from './state-storage.service';

export const UserRouteAccessService: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const accountService = inject(AccountService);
  const router = inject(Router);
  const stateStorageService = inject(StateStorageService);
  return accountService.identity().pipe(
    map(account => {
      if (account) {
        const authorities = next.data['authorities'];

        if (!authorities || authorities.length === 0 || accountService.hasAnyAuthority(authorities)) {
          return true;
        }

        if (isDevMode()) {
          console.error('User has not any of required authorities: ', authorities);
        }
        router.navigate(['accessdenied']);
        return false;
      }

      stateStorageService.storeUrl(state.url);
      router.navigate(['/login']);
      return false;
    }),
  );
};
