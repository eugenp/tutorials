jest.mock('app/core/auth/account.service');

import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { of, throwError } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';

import PasswordComponent from './password.component';
import { PasswordService } from './password.service';

describe('PasswordComponent', () => {
  let comp: PasswordComponent;
  let fixture: ComponentFixture<PasswordComponent>;
  let service: PasswordService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PasswordComponent],
      providers: [FormBuilder, AccountService],
    })
      .overrideTemplate(PasswordComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PasswordService);
  });

  it('should show error if passwords do not match', () => {
    // GIVEN
    comp.passwordForm.patchValue({
      newPassword: 'password1',
      confirmPassword: 'password2',
    });
    // WHEN
    comp.changePassword();
    // THEN
    expect(comp.doNotMatch()).toBe(true);
    expect(comp.error()).toBe(false);
    expect(comp.success()).toBe(false);
  });

  it('should call Auth.changePassword when passwords match', () => {
    // GIVEN
    const passwordValues = {
      currentPassword: 'oldPassword',
      newPassword: 'myPassword',
    };

    jest.spyOn(service, 'save').mockReturnValue(of(new HttpResponse({ body: true })));

    comp.passwordForm.patchValue({
      currentPassword: passwordValues.currentPassword,
      newPassword: passwordValues.newPassword,
      confirmPassword: passwordValues.newPassword,
    });

    // WHEN
    comp.changePassword();

    // THEN
    expect(service.save).toHaveBeenCalledWith(passwordValues.newPassword, passwordValues.currentPassword);
  });

  it('should set success to true upon success', () => {
    // GIVEN
    jest.spyOn(service, 'save').mockReturnValue(of(new HttpResponse({ body: true })));
    comp.passwordForm.patchValue({
      newPassword: 'myPassword',
      confirmPassword: 'myPassword',
    });

    // WHEN
    comp.changePassword();

    // THEN
    expect(comp.doNotMatch()).toBe(false);
    expect(comp.error()).toBe(false);
    expect(comp.success()).toBe(true);
  });

  it('should notify of error if change password fails', () => {
    // GIVEN
    jest.spyOn(service, 'save').mockReturnValue(throwError('ERROR'));
    comp.passwordForm.patchValue({
      newPassword: 'myPassword',
      confirmPassword: 'myPassword',
    });

    // WHEN
    comp.changePassword();

    // THEN
    expect(comp.doNotMatch()).toBe(false);
    expect(comp.success()).toBe(false);
    expect(comp.error()).toBe(true);
  });
});
