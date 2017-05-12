import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: 'jhi-health',
  component: JhiHealthCheckComponent,
  data: {
    pageTitle: 'health.title'
  }
};
