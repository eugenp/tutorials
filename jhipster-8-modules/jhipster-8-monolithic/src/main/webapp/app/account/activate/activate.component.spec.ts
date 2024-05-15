import { TestBed, waitForAsync, tick, fakeAsync, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

import { ActivateService } from './activate.service';
import ActivateComponent from './activate.component';

describe('ActivateComponent', () => {
  let comp: ActivateComponent;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ActivateComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { queryParams: of({ key: 'ABC123' }) },
        },
      ],
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
      jest.spyOn(service, 'get').mockReturnValue(of());

      comp.ngOnInit();
      tick();

      expect(service.get).toHaveBeenCalledWith('ABC123');
    }),
  ));

  it('should set set success to true upon successful activation', inject(
    [ActivateService],
    fakeAsync((service: ActivateService) => {
      jest.spyOn(service, 'get').mockReturnValue(of({}));

      comp.ngOnInit();
      tick();

      expect(comp.error()).toBe(false);
      expect(comp.success()).toBe(true);
    }),
  ));

  it('should set set error to true upon activation failure', inject(
    [ActivateService],
    fakeAsync((service: ActivateService) => {
      jest.spyOn(service, 'get').mockReturnValue(throwError('ERROR'));

      comp.ngOnInit();
      tick();

      expect(comp.error()).toBe(true);
      expect(comp.success()).toBe(false);
    }),
  ));
});
