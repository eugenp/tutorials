import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, map } from 'rxjs';
import { UserService } from './user.service';
import { baseUri } from '../app.config';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

enum LoginExperience {
  IFRAME,
  DEFAULT,
}

interface LoginOptionDto {
  label: string;
  loginUri: string;
  isSameAuthority: boolean;
}

function loginOptions(http: HttpClient): Observable<Array<LoginOptionDto>> {
  return http
    .get('/bff/login-options')
    .pipe(map((dto: any) => dto as LoginOptionDto[]));
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `<span>
      <select *ngIf="loginExperiences.length > 1" [formControl]="selectedLoginExperience">
        <option *ngFor="let le of loginExperiences">
          {{ loginExperienceLabel(le) }}
        </option>
      </select>
      <button (click)="login()"  [disabled]="!isLoginEnabled">Login</button>
    </span>
    <div
      class="modal-overlay"
      *ngIf="isLoginModalDisplayed && !isAuthenticated"
      (click)="isLoginModalDisplayed = false"
    >
      <div class="modal">
        <iframe
          [src]="iframeSrc"
          frameborder="0"
          (load)="onIframeLoad($event)"
        ></iframe>
        <button class="close-button" (click)="isLoginModalDisplayed = false">
          Discard
        </button>
      </div>
    </div>`,
  styles: `.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  .modal {
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    position: relative;
    width: 100%;
    max-width: 800px;
  }

  .modal iframe {
    width: 100%;
    height: 600px;
    border: none;
  }`,
})
export class LoginComponent {
  isLoginModalDisplayed = false;
  iframeSrc?: SafeUrl;
  loginExperiences: LoginExperience[] = [];
  selectedLoginExperience = new FormControl<LoginExperience | null>(null, [
    Validators.required,
  ]);

  private loginUri?: string;

  constructor(
    http: HttpClient,
    private user: UserService,
    private router: Router,
    private sanitizer: DomSanitizer
  ) {
    loginOptions(http).subscribe((opts) => {
      if (opts.length) {
        this.loginUri = opts[0].loginUri;
        if (opts[0].isSameAuthority) {
          this.loginExperiences.push(LoginExperience.IFRAME);
        }
        this.loginExperiences.push(LoginExperience.DEFAULT);
        this.selectedLoginExperience.patchValue(this.loginExperiences[0]);
      }
    });
  }

  get isLoginEnabled(): boolean {
    return (
      this.selectedLoginExperience.valid && !this.user.current.isAuthenticated
    );
  }

  get isAuthenticated(): boolean {
    return this.user.current.isAuthenticated;
  }

  login() {
    if (!this.loginUri) {
      return;
    }

    const url = new URL(this.loginUri);
    url.searchParams.append(
      'post_login_success_uri',
      `${baseUri}${this.router.url}`
    );
    url.searchParams.append(
      'post_login_failure_uri',
      `${baseUri}login-error`
    );
    const loginUrl = url.toString();

    if (this.selectedLoginExperience.value === LoginExperience.IFRAME) {
      this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(loginUrl);
      this.isLoginModalDisplayed = true;
    } else {
      window.location.href = loginUrl;
    }
  }

  onIframeLoad(event: any) {
    if (!!event.currentTarget.src) {
      this.user.refresh();
      this.isLoginModalDisplayed = !this.user.current.isAuthenticated;
    }
  }

  loginExperienceLabel(le: LoginExperience) {
    return LoginExperience[le].toLowerCase()
  }
}
