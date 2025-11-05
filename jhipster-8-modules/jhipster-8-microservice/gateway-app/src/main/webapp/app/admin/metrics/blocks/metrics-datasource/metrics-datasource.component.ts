import { ChangeDetectionStrategy, Component, input } from '@angular/core';

import SharedModule from 'app/shared/shared.module';
import { Databases } from 'app/admin/metrics/metrics.model';
import { filterNaN } from 'app/core/util/operators';

@Component({
  standalone: true,
  selector: 'jhi-metrics-datasource',
  templateUrl: './metrics-datasource.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [SharedModule],
})
export class MetricsDatasourceComponent {
  /**
   * object containing all datasource related metrics
   */
  datasourceMetrics = input<Databases>();

  /**
   * boolean field saying if the metrics are in the process of being updated
   */
  updating = input<boolean>();

  filterNaN = (n: number): number => filterNaN(n);
}
