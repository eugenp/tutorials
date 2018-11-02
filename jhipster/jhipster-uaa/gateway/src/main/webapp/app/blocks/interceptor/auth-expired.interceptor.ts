import { Injector } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { Principal } from 'app/core/auth/principal.service';
import { LoginService } from 'app/core/login/login.service';

export class AuthExpiredInterceptor implements HttpInterceptor {
    constructor(private injector: Injector) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {},
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        if (err.status === 401) {
                            const principal = this.injector.get(Principal);

                            if (principal.isAuthenticated()) {
                                principal.authenticate(null);
                                const loginModalService: LoginModalService = this.injector.get(LoginModalService);
                                loginModalService.open();
                            } else {
                                const loginService: LoginService = this.injector.get(LoginService);
                                loginService.logout();
                                const router = this.injector.get(Router);
                                router.navigate(['/']);
                            }
                        }
                    }
                }
            )
        );
    }
}
