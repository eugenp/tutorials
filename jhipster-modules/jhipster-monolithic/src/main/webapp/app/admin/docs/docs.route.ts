import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiDocsComponent } from './docs.component';

export const docsRoute: Route = {
  path: 'docs',
  component: JhiDocsComponent,
  data: {
    pageTitle: 'global.menu.admin.apidocs'
  }
};
