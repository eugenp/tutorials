import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ActivateComponent } from './activate.component';

export const activateRoute: Route = {
    path: 'activate',
    component: ActivateComponent,
    data: {
        authorities: [],
        pageTitle: 'activate.title'
    },
    canActivate: [UserRouteAccessService]
};
