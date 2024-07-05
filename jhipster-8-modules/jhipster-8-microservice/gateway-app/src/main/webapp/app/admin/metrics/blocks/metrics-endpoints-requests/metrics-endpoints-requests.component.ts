import { Component, input } from '@angular/core';

import SharedModule from 'app/shared/shared.module';
import { Services } from 'app/admin/metrics/metrics.model';

@Component({
  standalone: true,
  selector: 'jhi-metrics-endpoints-requests',
  templateUrl: './metrics-endpoints-requests.component.html',
  imports: [SharedModule],
})
export class MetricsEndpointsRequestsComponent {
  /**
   * object containing service related metrics
   */
  endpointsRequestsMetrics = input<Services>();

  /**
   * boolean field saying if the metrics are in the process of being updated
   */
  updating = input<boolean>();
}
