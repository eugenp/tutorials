import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MetricsService } from './metrics.service';
import { ThreadDump, ThreadState } from './metrics.model';

describe('Logs Service', () => {
  let service: MetricsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(MetricsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('Service methods', () => {
    it('should return Metrics', () => {
      let expectedResult;
      const metrics = {
        jvm: {},
        'http.server.requests': {},
        cache: {},
        services: {},
        databases: {},
        garbageCollector: {},
        processMetrics: {},
      };

      service.getMetrics().subscribe(received => {
        expectedResult = received;
      });

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(metrics);
      expect(expectedResult).toEqual(metrics);
    });

    it('should return Thread Dump', () => {
      let expectedResult: ThreadDump | null = null;
      const dump: ThreadDump = {
        threads: [
          {
            threadName: 'Reference Handler',
            threadId: 2,
            blockedTime: -1,
            blockedCount: 7,
            waitedTime: -1,
            waitedCount: 0,
            lockName: null,
            lockOwnerId: -1,
            lockOwnerName: null,
            daemon: true,
            inNative: false,
            suspended: false,
            threadState: ThreadState.Runnable,
            priority: 10,
            stackTrace: [],
            lockedMonitors: [],
            lockedSynchronizers: [],
            lockInfo: null,
          },
        ],
      };

      service.threadDump().subscribe(received => {
        expectedResult = received;
      });

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(dump);
      expect(expectedResult).toEqual(dump);
    });
  });
});
