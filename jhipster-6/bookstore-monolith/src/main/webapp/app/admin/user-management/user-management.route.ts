import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';

import { AccountService, User, UserService } from 'app/core';
import { UserMgmtComponent } from './user-management.component';
import { UserMgmtDetailComponent } from './user-management-detail.component';
import { UserMgmtUpdateComponent } from './user-management-update.component';

@Injectable({ providedIn: 'root' })
export class UserResolve implements CanActivate {
    constructor(private accountService: AccountService) {}

    canActivate() {
        return this.accountService.identity().then(account => this.accountService.hasAnyAuthority(['ROLE_ADMIN']));
    }
}

@Injectable({ providedIn: 'root' })
export class UserMgmtResolve implements Resolve<any> {
    constructor(private service: UserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['login'] ? route.params['login'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new User();
    }
}

export const userMgmtRoute: Routes = [
    {
        path: 'user-management',
        component: UserMgmtComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            pageTitle: 'Users',
            defaultSort: 'id,asc'
        }
    },
    {
        path: 'user-management/:login/view',
        component: UserMgmtDetailComponent,
        resolve: {
            user: UserMgmtResolve
        },
        data: {
            pageTitle: 'Users'
        }
    },
    {
        path: 'user-management/new',
        component: UserMgmtUpdateComponent,
        resolve: {
            user: UserMgmtResolve
        }
    },
    {
        path: 'user-management/:login/edit',
        component: UserMgmtUpdateComponent,
        resolve: {
            user: UserMgmtResolve
        }
    }
];
