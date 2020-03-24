import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: 'jhi-metrics',
  component: JhiMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
};
