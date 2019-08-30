import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class PasswordService {
    constructor(private http: HttpClient) {}

    save(newPassword: string, currentPassword: string): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/account/change-password', { currentPassword, newPassword });
    }
}
