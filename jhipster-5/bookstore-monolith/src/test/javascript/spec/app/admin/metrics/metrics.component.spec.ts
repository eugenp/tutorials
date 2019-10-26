import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { JhiMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { JhiMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
    describe('JhiMetricsMonitoringComponent', () => {
        let comp: JhiMetricsMonitoringComponent;
        let fixture: ComponentFixture<JhiMetricsMonitoringComponent>;
        let service: JhiMetricsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [JhiMetricsMonitoringComponent]
            })
                .overrideTemplate(JhiMetricsMonitoringComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JhiMetricsMonitoringComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JhiMetricsService);
        });

        describe('refresh', () => {
            it('should call refresh on init', () => {
                // GIVEN
                const response = {
                    timers: {
                        service: 'test',
                        unrelatedKey: 'test'
                    },
                    gauges: {
                        'jcache.statistics': {
                            value: 2
                        },
                        unrelatedKey: 'test'
                    }
                };
                spyOn(service, 'getMetrics').and.returnValue(of(response));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.getMetrics).toHaveBeenCalled();
            });
        });
    });
});
