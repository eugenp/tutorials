import { Component } from '@angular/core';
import { NavigationComponent } from './navigation.component';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [NavigationComponent],
  template: `<app-navigation
      [destination]="['']"
      label="HOME"
    ></app-navigation>
    <p>
      This application is a show-case for an Angular app consuming a REST API
      through an OAuth2 BFF.
    </p>`,
  styles: ``,
})
export class AboutView {}
