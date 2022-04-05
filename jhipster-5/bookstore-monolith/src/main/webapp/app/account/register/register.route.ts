import { Route } from '@angular/router';

import { RegisterComponent } from './register.component';

export const registerRoute: Route = {
    path: 'register',
    component: RegisterComponent,
    data: {
        authorities: [],
        pageTitle: 'Registration'
    }
};
