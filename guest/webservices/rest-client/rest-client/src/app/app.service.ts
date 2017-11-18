import {Injectable} from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

export class User {
  constructor(
    public email: string,
    public name: string) { }
} 
 
@Injectable()
export class UserService {
  constructor(
    private _http: Http){}
    
    url = 'http://localhost:8080/rest-server/users';
	
    addUser(user){   
      let headers = new Headers({'Content-Type': 'application/json'});
      let options = new RequestOptions({ headers: headers});
     
      return this._http.post(this.url, JSON.stringify(user), options)
        .map( 
		  (_response: Response) => {
            return _response;
		  },
          err => alert('Error adding user')); 
    }
    
    getUsers() {
      return this._http.get(this.url)
        .map((_response: Response) => {
          return _response.json();
        });
    }    
}