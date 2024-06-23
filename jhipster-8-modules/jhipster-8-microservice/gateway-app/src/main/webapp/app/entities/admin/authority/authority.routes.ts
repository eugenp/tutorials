import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuthorityComponent } from './list/authority.component';
import { AuthorityDetailComponent } from './detail/authority-detail.component';
import { AuthorityUpdateComponent } from './update/authority-update.component';
import AuthorityResolve from './route/authority-routing-resolve.service';

const authorityRoute: Routes = [
  {
    path: '',
    component: AuthorityComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':name/view',
    component: AuthorityDetailComponent,
    resolve: {
      authority: AuthorityResolve,
    },
    data: {
      authorities: ['ROLE_ADMIN'],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuthorityUpdateComponent,
    resolve: {
      authority: AuthorityResolve,
    },
    data: {
      authorities: ['ROLE_ADMIN'],
    },
    canActivate: [UserRouteAccessService],
  },
];

export default authorityRoute;
