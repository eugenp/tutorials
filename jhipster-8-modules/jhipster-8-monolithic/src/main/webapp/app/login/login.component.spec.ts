jest.mock('app/core/auth/account.service');
jest.mock('app/login/login.service');

import { ElementRef } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router, Navigation } from '@angular/router';
import { of, throwError } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';

import { LoginService } from './login.service';
import LoginComponent from './login.component';

describe('LoginComponent', () => {
  let comp: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockRouter: Router;
  let mockAccountService: AccountService;
  let mockLoginService: LoginService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        FormBuilder,
        AccountService,
        {
          provide: LoginService,
          useValue: {
            login: jest.fn(() => of({})),
          },
        },
      ],
    })
      .overrideTemplate(LoginComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    comp = fixture.componentInstance;
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockLoginService = TestBed.inject(LoginService);
    mockAccountService = TestBed.inject(AccountService);
  });

  describe('ngOnInit', () => {
    it('Should call accountService.identity on Init', () => {
      // GIVEN
      mockAccountService.identity = jest.fn(() => of(null));
      mockAccountService.getAuthenticationState = jest.fn(() => of(null));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(mockAccountService.identity).toHaveBeenCalled();
    });

    it('Should call accountService.isAuthenticated on Init', () => {
      // GIVEN
      mockAccountService.identity = jest.fn(() => of(null));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(mockAccountService.isAuthenticated).toHaveBeenCalled();
    });

    it('should navigate to home page on Init if authenticated=true', () => {
      // GIVEN
      mockAccountService.identity = jest.fn(() => of(null));
      mockAccountService.getAuthenticationState = jest.fn(() => of(null));
      mockAccountService.isAuthenticated = () => true;

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(mockRouter.navigate).toHaveBeenCalledWith(['']);
    });
  });

  describe('ngAfterViewInit', () => {
    it('should set focus to username input after the view has been initialized', () => {
      // GIVEN
      const node = {
        focus: jest.fn(),
      };
      comp.username = new ElementRef(node);

      // WHEN
      comp.ngAfterViewInit();

      // THEN
      expect(node.focus).toHaveBeenCalled();
    });
  });

  describe('login', () => {
    it('should authenticate the user and navigate to home page', () => {
      // GIVEN
      const credentials = {
        username: 'admin',
        password: 'admin',
        rememberMe: true,
      };

      comp.loginForm.patchValue({
        username: 'admin',
        password: 'admin',
        rememberMe: true,
      });

      // WHEN
      comp.login();

      // THEN
      expect(comp.authenticationError()).toEqual(false);
      expect(mockLoginService.login).toHaveBeenCalledWith(credentials);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['']);
    });

    it('should authenticate the user but not navigate to home page if authentication process is already routing to cached url from localstorage', () => {
      // GIVEN
      jest.spyOn(mockRouter, 'getCurrentNavigation').mockReturnValue({} as Navigation);

      // WHEN
      comp.login();

      // THEN
      expect(comp.authenticationError()).toEqual(false);
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });

    it('should stay on login form and show error message on login error', () => {
      // GIVEN
      mockLoginService.login = jest.fn(() => throwError(() => {}));

      // WHEN
      comp.login();

      // THEN
      expect(comp.authenticationError()).toEqual(true);
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });
  });
});
