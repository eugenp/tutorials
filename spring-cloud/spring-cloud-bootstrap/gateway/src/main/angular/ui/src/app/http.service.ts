import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers, RequestOptions} from "@angular/http";

@Injectable()
export class HttpService {

  constructor(private http: Http) { }

  login(user: any): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    return this.http.get("/me", options)
  }

  logout(user: any): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    return this.http.post("/logout", '', options)
  }
}
