import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class AuditsService  {
    constructor(private http: Http) { }

    query(req: any): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        params.set('fromDate', req.fromDate);
        params.set('toDate', req.toDate);
        params.set('page', req.page);
        params.set('size', req.size);
        params.set('sort', req.sort);

        let options = {
            search: params
        };

        return this.http.get('management/audits', options);
    }
}
