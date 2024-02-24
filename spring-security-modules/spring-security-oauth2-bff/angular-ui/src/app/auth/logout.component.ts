import { Component } from '@angular/core';
import { UserService } from './user.service';
import { lastValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { baseUri } from '../app.config';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  template: `
  <button (click)="logout()">Logout</button>
  `,
  styles: ``
})
export class LogoutComponent {

  constructor(private http: HttpClient, private user: UserService) {}

  logout() {
    lastValueFrom(
      this.http.post('/bff/logout', null, {
        headers: {
          'X-POST-LOGOUT-SUCCESS-URI': baseUri,
        },
        observe: 'response',
      })
    )
      .then((resp) => {
        const logoutUri = resp.headers.get('Location');
        if (!!logoutUri) {
          window.location.href = logoutUri;
        }
      })
      .finally(() => {
        this.user.refresh();
      });
  }
}
