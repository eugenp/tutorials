import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../shared';
import { PasswordResetInitComponent } from './password-reset-init.component';

export const passwordResetInitRoute: Route = {
  path: 'reset/request',
  component: PasswordResetInitComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  },
  canActivate: [UserRouteAccessService]
};
