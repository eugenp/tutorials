import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, inject, signal } from '@angular/core';
import { combineLatest } from 'rxjs';

import SharedModule from 'app/shared/shared.module';
import { MetricsService } from './metrics.service';
import { Metrics, Thread } from './metrics.model';
import { JvmMemoryComponent } from './blocks/jvm-memory/jvm-memory.component';
import { JvmThreadsComponent } from './blocks/jvm-threads/jvm-threads.component';
import { MetricsCacheComponent } from './blocks/metrics-cache/metrics-cache.component';
import { MetricsDatasourceComponent } from './blocks/metrics-datasource/metrics-datasource.component';
import { MetricsEndpointsRequestsComponent } from './blocks/metrics-endpoints-requests/metrics-endpoints-requests.component';
import { MetricsGarbageCollectorComponent } from './blocks/metrics-garbagecollector/metrics-garbagecollector.component';
import { MetricsModalThreadsComponent } from './blocks/metrics-modal-threads/metrics-modal-threads.component';
import { MetricsRequestComponent } from './blocks/metrics-request/metrics-request.component';
import { MetricsSystemComponent } from './blocks/metrics-system/metrics-system.component';

@Component({
  standalone: true,
  selector: 'jhi-metrics',
  templateUrl: './metrics.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    SharedModule,
    JvmMemoryComponent,
    JvmThreadsComponent,
    MetricsCacheComponent,
    MetricsDatasourceComponent,
    MetricsEndpointsRequestsComponent,
    MetricsGarbageCollectorComponent,
    MetricsModalThreadsComponent,
    MetricsRequestComponent,
    MetricsSystemComponent,
  ],
})
export default class MetricsComponent implements OnInit {
  metrics = signal<Metrics | undefined>(undefined);
  threads = signal<Thread[] | undefined>(undefined);
  updatingMetrics = signal(true);

  private metricsService = inject(MetricsService);
  private changeDetector = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.updatingMetrics.set(true);
    combineLatest([this.metricsService.getMetrics(), this.metricsService.threadDump()]).subscribe(([metrics, threadDump]) => {
      this.metrics.set(metrics);
      this.threads.set(threadDump.threads);
      this.updatingMetrics.set(false);
      this.changeDetector.markForCheck();
    });
  }

  metricsKeyExists(key: keyof Metrics): boolean {
    return Boolean(this.metrics()?.[key]);
  }

  metricsKeyExistsAndObjectNotEmpty(key: keyof Metrics): boolean {
    return Boolean(this.metrics()?.[key] && JSON.stringify(this.metrics()?.[key]) !== '{}');
  }
}
