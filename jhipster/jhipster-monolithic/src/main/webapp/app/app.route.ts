import { Route } from '@angular/router';

import { NavbarComponent } from './layouts';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
  };
