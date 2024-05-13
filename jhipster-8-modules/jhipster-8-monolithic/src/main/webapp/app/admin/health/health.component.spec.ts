import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';

import HealthComponent from './health.component';
import { HealthService } from './health.service';
import { Health } from './health.model';

describe('HealthComponent', () => {
  let comp: HealthComponent;
  let fixture: ComponentFixture<HealthComponent>;
  let service: HealthService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HealthComponent],
    })
      .overrideTemplate(HealthComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HealthService);
  });

  describe('getBadgeClass', () => {
    it('should get badge class', () => {
      const upBadgeClass = comp.getBadgeClass('UP');
      const downBadgeClass = comp.getBadgeClass('DOWN');
      expect(upBadgeClass).toEqual('bg-success');
      expect(downBadgeClass).toEqual('bg-danger');
    });
  });

  describe('refresh', () => {
    it('should call refresh on init', () => {
      // GIVEN
      const health: Health = { status: 'UP', components: { mail: { status: 'UP', details: { mailDetail: 'mail' } } } };
      jest.spyOn(service, 'checkHealth').mockReturnValue(of(health));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.checkHealth).toHaveBeenCalled();
      expect(comp.health).toEqual(health);
    });

    it('should handle a 503 on refreshing health data', () => {
      // GIVEN
      const health: Health = { status: 'DOWN', components: { mail: { status: 'DOWN' } } };
      jest.spyOn(service, 'checkHealth').mockReturnValue(throwError(new HttpErrorResponse({ status: 503, error: health })));

      // WHEN
      comp.refresh();

      // THEN
      expect(service.checkHealth).toHaveBeenCalled();
      expect(comp.health).toEqual(health);
    });
  });
});
