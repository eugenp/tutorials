jest.mock('app/core/auth/account.service');

import { Component, ElementRef, ViewChild, WritableSignal, signal } from '@angular/core';
import { TestBed, waitForAsync } from '@angular/core/testing';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import HasAnyAuthorityDirective from './has-any-authority.directive';

@Component({
  template: ` <div *jhiHasAnyAuthority="'ROLE_ADMIN'" #content></div> `,
})
class TestHasAnyAuthorityDirectiveComponent {
  @ViewChild('content', { static: false })
  content?: ElementRef;
}

describe('HasAnyAuthorityDirective tests', () => {
  let mockAccountService: AccountService;
  let currentAccount: WritableSignal<Account | null>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HasAnyAuthorityDirective],
      declarations: [TestHasAnyAuthorityDirectiveComponent],
      providers: [AccountService],
    });
  }));

  beforeEach(() => {
    mockAccountService = TestBed.inject(AccountService);
    currentAccount = signal<Account | null>({ activated: true, authorities: [] } as any);
    mockAccountService.trackCurrentAccount = jest.fn(() => currentAccount);
  });

  describe('set jhiHasAnyAuthority', () => {
    it('should show restricted content to user if user has required role', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => true);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;
      fixture.detectChanges();

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();
    });

    it('should not show restricted content to user if user has not required role', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => false);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;
      fixture.detectChanges();

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeUndefined();
    });
  });

  describe('change authorities', () => {
    it('should show or not show restricted content correctly if user authorities are changing', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn((): boolean => Boolean(currentAccount()));
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;
      fixture.detectChanges();

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();

      // GIVEN
      currentAccount.set(null);

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeUndefined();

      // WHEN
      currentAccount.set({ activated: true, authorities: ['foo'] } as any);
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();
    });
  });
});
