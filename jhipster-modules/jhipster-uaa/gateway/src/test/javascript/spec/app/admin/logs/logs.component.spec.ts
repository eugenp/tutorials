import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { LogsComponent } from 'app/admin/logs/logs.component';
import { LogsService } from 'app/admin/logs/logs.service';
import { ITEMS_PER_PAGE } from 'app/shared';
import { Log } from 'app/admin';

describe('Component Tests', () => {
    describe('LogsComponent', () => {
        let comp: LogsComponent;
        let fixture: ComponentFixture<LogsComponent>;
        let service: LogsService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [GatewayTestModule],
                    declarations: [LogsComponent],
                    providers: [LogsService]
                })
                    .overrideTemplate(LogsComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(LogsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogsService);
        });

        describe('OnInit', () => {
            it('should set all default values correctly', () => {
                expect(comp.filter).toBe('');
                expect(comp.orderProp).toBe('name');
                expect(comp.reverse).toBe(false);
            });
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                const log = new Log('main', 'WARN');
                spyOn(service, 'findAll').and.returnValue(
                    of(
                        new HttpResponse({
                            body: [log],
                            headers
                        })
                    )
                );

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.findAll).toHaveBeenCalled();
                expect(comp.loggers[0]).toEqual(jasmine.objectContaining(log));
            });
        });
        describe('change log level', () => {
            it('should change log level correctly', () => {
                // GIVEN
                const log = new Log('main', 'ERROR');
                spyOn(service, 'changeLevel').and.returnValue(of(new HttpResponse()));
                spyOn(service, 'findAll').and.returnValue(of(new HttpResponse({ body: [log] })));

                // WHEN
                comp.changeLevel('main', 'ERROR');

                // THEN
                expect(service.changeLevel).toHaveBeenCalled();
                expect(service.findAll).toHaveBeenCalled();
                expect(comp.loggers[0]).toEqual(jasmine.objectContaining(log));
            });
        });
    });
});
