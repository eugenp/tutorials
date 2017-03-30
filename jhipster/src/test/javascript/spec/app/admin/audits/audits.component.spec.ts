import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { NgbPaginationConfig} from '@ng-bootstrap/ng-bootstrap';
import { ParseLinks } from 'ng-jhipster';
import { BaeldungTestModule } from '../../../test.module';
import { PaginationConfig } from '../../../../../../main/webapp/app/blocks/config/uib-pagination.config'
import { AuditsComponent } from '../../../../../../main/webapp/app/admin/audits/audits.component';
import { AuditsService } from '../../../../../../main/webapp/app/admin/audits/audits.service';
import { ITEMS_PER_PAGE } from '../../../../../../main/webapp/app/shared';


function getDate(isToday= true){
    let date: Date = new Date();
    if (isToday) {
        // Today + 1 day - needed if the current day must be included
        date.setDate(date.getDate() + 1);
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
    }
    return `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`;
}

describe('Component Tests', () => {

    describe('AuditsComponent', () => {

        let comp: AuditsComponent;
        let fixture: ComponentFixture<AuditsComponent>;
        let service: AuditsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BaeldungTestModule],
                declarations: [AuditsComponent],
                providers: [
                    AuditsService,
                    NgbPaginationConfig,
                    ParseLinks,
                    PaginationConfig,
                    DatePipe
                ]
            })
            .overrideComponent(AuditsComponent, {
                set: {
                    template: ''
                }
            })
            .compileComponents();
        }));

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
            it('should set toDate to current date', () => {
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
               expect(comp.page).toBe(1);
               expect(comp.reverse).toBeFalsy();
               expect(comp.orderProp).toBe('timestamp');
            });
        });
    });
});
