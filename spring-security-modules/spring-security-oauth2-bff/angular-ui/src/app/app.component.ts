import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthenticationComponent } from './auth/authentication.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HttpClientModule,
    AuthenticationComponent,
  ],
  template: `<div style="display: flex;">
      <div style="margin: auto;"></div>
      <h1>Angular UI</h1>
      <div style="margin: auto;"></div>
      <app-authentication style="margin: auto 1em;"></app-authentication>
    </div>
    <div>
      <router-outlet></router-outlet>
    </div>`,
  styles: [],
})
export class AppComponent {}
