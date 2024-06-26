jest.mock('app/core/auth/account.service');

import { waitForAsync, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router, TitleStrategy } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { DOCUMENT } from '@angular/common';
import { Component, NgZone } from '@angular/core';
import { of } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';

import { AppPageTitleStrategy } from 'app/app-page-title-strategy';
import MainComponent from './main.component';

describe('MainComponent', () => {
  let comp: MainComponent;
  let fixture: ComponentFixture<MainComponent>;
  let titleService: Title;
  let mockAccountService: AccountService;
  let ngZone: NgZone;
  const routerState: any = { snapshot: { root: { data: {} } } };
  let router: Router;
  let document: Document;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [MainComponent],
      providers: [Title, AccountService, { provide: TitleStrategy, useClass: AppPageTitleStrategy }],
    })
      .overrideTemplate(MainComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainComponent);
    comp = fixture.componentInstance;
    titleService = TestBed.inject(Title);
    mockAccountService = TestBed.inject(AccountService);
    mockAccountService.identity = jest.fn(() => of(null));
    mockAccountService.getAuthenticationState = jest.fn(() => of(null));
    ngZone = TestBed.inject(NgZone);
    router = TestBed.inject(Router);
    document = TestBed.inject(DOCUMENT);
  });

  describe('page title', () => {
    const defaultPageTitle = 'Gateway App';
    const parentRoutePageTitle = 'parentTitle';
    const childRoutePageTitle = 'childTitle';

    beforeEach(() => {
      routerState.snapshot.root = { data: {} };
      jest.spyOn(titleService, 'setTitle');
      comp.ngOnInit();
    });

    describe('navigation end', () => {
      it('should set page title to default title if pageTitle is missing on routes', fakeAsync(() => {
        // WHEN
        ngZone.run(() => router.navigateByUrl(''));
        tick();

        // THEN
        expect(document.title).toBe(defaultPageTitle);
      }));

      it('should set page title to root route pageTitle if there is no child routes', fakeAsync(() => {
        // GIVEN
        router.resetConfig([{ path: '', title: parentRoutePageTitle, component: BlankComponent }]);

        // WHEN
        ngZone.run(() => router.navigateByUrl(''));
        tick();

        // THEN
        expect(document.title).toBe(parentRoutePageTitle);
      }));

      it('should set page title to child route pageTitle if child routes exist and pageTitle is set for child route', fakeAsync(() => {
        // GIVEN
        router.resetConfig([
          {
            path: 'home',
            title: parentRoutePageTitle,
            children: [{ path: '', title: childRoutePageTitle, component: BlankComponent }],
          },
        ]);

        // WHEN
        ngZone.run(() => router.navigateByUrl('home'));
        tick();

        // THEN
        expect(document.title).toBe(childRoutePageTitle);
      }));

      it('should set page title to parent route pageTitle if child routes exists but pageTitle is not set for child route data', fakeAsync(() => {
        // GIVEN
        router.resetConfig([
          {
            path: 'home',
            title: parentRoutePageTitle,
            children: [{ path: '', component: BlankComponent }],
          },
        ]);

        // WHEN
        ngZone.run(() => router.navigateByUrl('home'));
        tick();

        // THEN
        expect(document.title).toBe(parentRoutePageTitle);
      }));
    });
  });
});

@Component({ template: '' })
export class BlankComponent {}
