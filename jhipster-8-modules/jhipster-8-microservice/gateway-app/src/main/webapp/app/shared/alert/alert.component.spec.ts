jest.mock('app/core/util/alert.service');

import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AlertService } from 'app/core/util/alert.service';

import { AlertComponent } from './alert.component';

describe('Alert Component', () => {
  let comp: AlertComponent;
  let fixture: ComponentFixture<AlertComponent>;
  let mockAlertService: AlertService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [AlertComponent],
      providers: [AlertService],
    })
      .overrideTemplate(AlertComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertComponent);
    comp = fixture.componentInstance;
    mockAlertService = TestBed.inject(AlertService);
  });

  it('Should call alertService.get on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(mockAlertService.get).toHaveBeenCalled();
  });

  it('Should call alertService.clear on destroy', () => {
    // WHEN
    comp.ngOnDestroy();

    // THEN
    expect(mockAlertService.clear).toHaveBeenCalled();
  });
});
