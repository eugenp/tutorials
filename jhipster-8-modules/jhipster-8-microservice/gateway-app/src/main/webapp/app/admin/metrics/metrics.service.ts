import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Metrics, ThreadDump } from './metrics.model';

@Injectable({ providedIn: 'root' })
export class MetricsService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  getMetrics(): Observable<Metrics> {
    return this.http.get<Metrics>(this.applicationConfigService.getEndpointFor('management/jhimetrics'));
  }

  threadDump(): Observable<ThreadDump> {
    return this.http.get<ThreadDump>(this.applicationConfigService.getEndpointFor('management/threaddump'));
  }
}
