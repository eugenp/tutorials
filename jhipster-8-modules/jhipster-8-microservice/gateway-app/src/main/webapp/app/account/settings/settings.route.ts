import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SettingsComponent from './settings.component';

const settingsRoute: Route = {
  path: 'settings',
  component: SettingsComponent,
  title: 'Settings',
  canActivate: [UserRouteAccessService],
};

export default settingsRoute;
