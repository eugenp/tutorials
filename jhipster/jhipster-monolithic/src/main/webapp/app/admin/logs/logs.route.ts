import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LogsComponent } from './logs.component';

export const logsRoute: Route = {
  path: 'logs',
  component: LogsComponent,
  data: {
    pageTitle: 'logs.title'
  }
};
