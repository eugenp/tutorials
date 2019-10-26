import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Post } from './post.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class PostService {

    private resourceUrl = 'api/posts';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(post: Post): Observable<Post> {
        let copy: Post = Object.assign({}, post);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(post.creationDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(post: Post): Observable<Post> {
        let copy: Post = Object.assign({}, post);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(post.creationDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Post> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.creationDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.creationDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].creationDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].creationDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
