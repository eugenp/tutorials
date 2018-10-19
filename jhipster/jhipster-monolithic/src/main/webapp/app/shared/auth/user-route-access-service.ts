import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';

import { AuthService } from '../';

@Injectable()
export class UserRouteAccessService implements CanActivate {

    constructor(private router: Router, private auth: AuthService) {
    }

    canActivate(route: ActivatedRouteSnapshot): boolean | Promise<boolean> {
        return this.auth.authorize(false).then( canActivate => canActivate);
    }
}
