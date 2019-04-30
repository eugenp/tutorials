import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { GatewayRoute } from './gateway-route.model';

@Injectable()
export class GatewayRoutesService {
    constructor(private http: HttpClient) {}

    findAll(): Observable<GatewayRoute[]> {
        return this.http.get<GatewayRoute[]>(SERVER_API_URL + 'api/gateway/routes/');
    }
}
