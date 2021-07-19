import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { AuditsComponent } from 'app/admin/audits/audits.component';
import { AuditsService } from 'app/admin/audits/audits.service';
import { Audit } from 'app/admin/audits/audit.model';
import { ITEMS_PER_PAGE } from 'app/shared';

function build2DigitsDatePart(datePart: number) {
    return `0${datePart}`.slice(-2);
}

function getDate(isToday = true) {
    let date: Date = new Date();
    if (isToday) {
        // Today + 1 day - needed if the current day must be included
        date.setDate(date.getDate() + 1);
    } else {
        // get last month
        if (date.getMonth() === 0) {
            date = new Date(date.getFullYear() - 1, 11, date.getDate());
        } else {
            date = new Date(date.getFullYear(), date.getMonth() - 1, date.getDate());
        }
    }
    const monthString = build2DigitsDatePart(date.getMonth() + 1);
    const dateString = build2DigitsDatePart(date.getDate());
    return `${date.getFullYear()}-${monthString}-${dateString}`;
}

describe('Component Tests', () => {
    describe('AuditsComponent', () => {
        let comp: AuditsComponent;
        let fixture: ComponentFixture<AuditsComponent>;
        let service: AuditsService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [GatewayTestModule],
                    declarations: [AuditsComponent],
                    providers: [AuditsService]
                })
                    .overrideTemplate(AuditsComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(AuditsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuditsService);
        });

        describe('today function ', () => {
            it('should set toDate to current date', () => {
                comp.today();
                expect(comp.toDate).toBe(getDate());
            });
        });

        describe('previousMonth function ', () => {
            it('should set fromDate to current date', () => {
                comp.previousMonth();
                expect(comp.fromDate).toBe(getDate(false));
            });
        });

        describe('By default, on init', () => {
            it('should set all default values correctly', () => {
                fixture.detectChanges();
                expect(comp.toDate).toBe(getDate());
                expect(comp.fromDate).toBe(getDate(false));
                expect(comp.itemsPerPage).toBe(ITEMS_PER_PAGE);
                expect(comp.page).toBe(10);
                expect(comp.reverse).toBeFalsy();
                expect(comp.predicate).toBe('id');
            });
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                const audit = new Audit({ remoteAddress: '127.0.0.1', sessionId: '123' }, 'user', '20140101', 'AUTHENTICATION_SUCCESS');
                spyOn(service, 'query').and.returnValue(
                    of(
                        new HttpResponse({
                            body: [audit],
                            headers
                        })
                    )
                );

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.audits[0]).toEqual(jasmine.objectContaining(audit));
            });
        });

        describe('Create sort object', () => {
            it('Should sort only by id asc', () => {
                // GIVEN
                comp.predicate = 'id';
                comp.reverse = false;

                // WHEN
                const sort = comp.sort();

                // THEN
                expect(sort.length).toEqual(1);
                expect(sort[0]).toEqual('id,desc');
            });

            it('Should sort by timestamp asc then by id', () => {
                // GIVEN
                comp.predicate = 'timestamp';
                comp.reverse = true;

                // WHEN
                const sort = comp.sort();

                // THEN
                expect(sort.length).toEqual(2);
                expect(sort[0]).toEqual('timestamp,asc');
                expect(sort[1]).toEqual('id');
            });
        });
    });
});
