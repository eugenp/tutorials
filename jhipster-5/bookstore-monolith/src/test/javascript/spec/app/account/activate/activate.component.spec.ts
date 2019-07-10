import { TestBed, async, tick, fakeAsync, inject } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActivateService } from 'app/account/activate/activate.service';
import { ActivateComponent } from 'app/account/activate/activate.component';

describe('Component Tests', () => {
    describe('ActivateComponent', () => {
        let comp: ActivateComponent;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [ActivateComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({ key: 'ABC123' })
                    }
                ]
            })
                .overrideTemplate(ActivateComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            const fixture = TestBed.createComponent(ActivateComponent);
            comp = fixture.componentInstance;
        });

        it('calls activate.get with the key from params', inject(
            [ActivateService],
            fakeAsync((service: ActivateService) => {
                spyOn(service, 'get').and.returnValue(of());

                comp.ngOnInit();
                tick();

                expect(service.get).toHaveBeenCalledWith('ABC123');
            })
        ));

        it('should set set success to OK upon successful activation', inject(
            [ActivateService],
            fakeAsync((service: ActivateService) => {
                spyOn(service, 'get').and.returnValue(of({}));

                comp.ngOnInit();
                tick();

                expect(comp.error).toBe(null);
                expect(comp.success).toEqual('OK');
            })
        ));

        it('should set set error to ERROR upon activation failure', inject(
            [ActivateService],
            fakeAsync((service: ActivateService) => {
                spyOn(service, 'get').and.returnValue(throwError('ERROR'));

                comp.ngOnInit();
                tick();

                expect(comp.error).toBe('ERROR');
                expect(comp.success).toEqual(null);
            })
        ));
    });
});
