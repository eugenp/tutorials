import { ChangeDetectionStrategy, Component, input } from '@angular/core';

import SharedModule from 'app/shared/shared.module';
import { HttpServerRequests } from 'app/admin/metrics/metrics.model';
import { filterNaN } from 'app/core/util/operators';

@Component({
  standalone: true,
  selector: 'jhi-metrics-request',
  templateUrl: './metrics-request.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [SharedModule],
})
export class MetricsRequestComponent {
  /**
   * object containing http request related metrics
   */
  requestMetrics = input<HttpServerRequests>();

  /**
   * boolean field saying if the metrics are in the process of being updated
   */
  updating = input<boolean>();

  filterNaN = (n: number): number => filterNaN(n);
}
