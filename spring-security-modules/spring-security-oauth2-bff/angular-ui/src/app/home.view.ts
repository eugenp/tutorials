import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { NavigationComponent } from './navigation.component';
import { User, UserService } from './auth/user.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavigationComponent],
  template: `<app-navigation
      [destination]="['about']"
      label="About"
    ></app-navigation>
    <p>{{ message }}</p>`,
  styles: [],
})
export class HomeView {
  message = '';

  private userSubscription?: Subscription;

  constructor(user: UserService) {
    this.userSubscription = user.valueChanges.subscribe((u) => {
      this.message = u.isAuthenticated
        ? `Hi ${u.name}, you are granted with ${HomeView.rolesStr(u)}.`
        : 'You are not authenticated.';
    });
  }

  static rolesStr(user: User) {
    if(!user?.roles?.length) {
      return '[]'
    }
    return `["${user.roles.join('", "')}"]`
  }

  ngOnDestroy() {
    this.userSubscription?.unsubscribe();
  }
}
