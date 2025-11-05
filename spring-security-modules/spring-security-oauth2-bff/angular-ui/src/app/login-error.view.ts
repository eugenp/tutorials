import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login-error',
  standalone: true,
  imports: [],
  template: `
    <h1>Login Error</h1>
    <p>{{ msg }}</p>
  `,
  styles: ``
})
export class LoginErrorView {
  msg = ""

  constructor(activatedRoute: ActivatedRoute) {
    activatedRoute.queryParams.subscribe((params) => {
      this.msg = params['error'];
    })
  }
}
