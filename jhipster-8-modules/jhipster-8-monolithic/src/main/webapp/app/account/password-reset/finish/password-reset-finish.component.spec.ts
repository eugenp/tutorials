import { ElementRef } from '@angular/core';
import { ComponentFixture, TestBed, inject, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

import PasswordResetFinishComponent from './password-reset-finish.component';
import { PasswordResetFinishService } from './password-reset-finish.service';

describe('PasswordResetFinishComponent', () => {
  let fixture: ComponentFixture<PasswordResetFinishComponent>;
  let comp: PasswordResetFinishComponent;

  beforeEach(() => {
    fixture = TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PasswordResetFinishComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: { queryParams: of({ key: 'XYZPDQ' }) },
        },
      ],
    })
      .overrideTemplate(PasswordResetFinishComponent, '')
      .createComponent(PasswordResetFinishComponent);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordResetFinishComponent);
    comp = fixture.componentInstance;
    comp.ngOnInit();
  });

  it('should define its initial state', () => {
    expect(comp.initialized()).toBe(true);
    expect(comp.key()).toEqual('XYZPDQ');
  });

  it('sets focus after the view has been initialized', () => {
    const node = {
      focus: jest.fn(),
    };
    comp.newPassword = new ElementRef(node);

    comp.ngAfterViewInit();

    expect(node.focus).toHaveBeenCalled();
  });

  it('should ensure the two passwords entered match', () => {
    comp.passwordForm.patchValue({
      newPassword: 'password',
      confirmPassword: 'non-matching',
    });

    comp.finishReset();

    expect(comp.doNotMatch()).toBe(true);
  });

  it('should update success to true after resetting password', inject(
    [PasswordResetFinishService],
    fakeAsync((service: PasswordResetFinishService) => {
      jest.spyOn(service, 'save').mockReturnValue(of({}));
      comp.passwordForm.patchValue({
        newPassword: 'password',
        confirmPassword: 'password',
      });

      comp.finishReset();
      tick();

      expect(service.save).toHaveBeenCalledWith('XYZPDQ', 'password');
      expect(comp.success()).toBe(true);
    }),
  ));

  it('should notify of generic error', inject(
    [PasswordResetFinishService],
    fakeAsync((service: PasswordResetFinishService) => {
      jest.spyOn(service, 'save').mockReturnValue(throwError('ERROR'));
      comp.passwordForm.patchValue({
        newPassword: 'password',
        confirmPassword: 'password',
      });

      comp.finishReset();
      tick();

      expect(service.save).toHaveBeenCalledWith('XYZPDQ', 'password');
      expect(comp.success()).toBe(false);
      expect(comp.error()).toBe(true);
    }),
  ));
});
