import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: 'jhi-configuration',
  component: JhiConfigurationComponent,
  data: {
    pageTitle: 'configuration.title'
  }
};
