import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [],
  template: `<button (click)="navigate()">{{ label }}</button>`,
  styles: ``,
})
export class NavigationComponent {
  @Input()
  label!: string;

  @Input()
  destination!: string[];

  private userSubscription?: Subscription;

  constructor(private router: Router) {}

  ngOnDestroy() {
    this.userSubscription?.unsubscribe();
  }

  navigate() {
    this.router.navigate(this.destination);
  }
}
