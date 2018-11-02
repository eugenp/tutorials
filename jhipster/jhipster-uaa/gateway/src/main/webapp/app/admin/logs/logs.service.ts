import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { Log } from './log.model';

@Injectable({ providedIn: 'root' })
export class LogsService {
    constructor(private http: HttpClient) {}

    changeLevel(log: Log): Observable<HttpResponse<any>> {
        return this.http.put(SERVER_API_URL + 'management/logs', log, { observe: 'response' });
    }

    findAll(): Observable<HttpResponse<Log[]>> {
        return this.http.get<Log[]>(SERVER_API_URL + 'management/logs', { observe: 'response' });
    }
}
