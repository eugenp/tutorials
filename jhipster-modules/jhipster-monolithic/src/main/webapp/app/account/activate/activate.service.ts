import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class Activate {

    constructor (private http: Http) {}

    get(key: string): Observable<any> {
        let params: URLSearchParams = new URLSearchParams();
        params.set('key', key);

        return this.http.get('api/activate', {
            search: params
        }).map((res: Response) => res);
    }
}
