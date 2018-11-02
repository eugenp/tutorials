import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
};
