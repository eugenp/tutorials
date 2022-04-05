import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap} from 'rxjs/operators';
@Component({
    selector: 'home',
    templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

    userName: string;

    constructor(private http: HttpClient) { }

    ngOnInit() {
        let url = 'http://localhost:8082/user';

        let headers: HttpHeaders = new HttpHeaders({
            'Authorization': 'Basic ' + sessionStorage.getItem('token')
        });

        let options = { headers: headers };
        this.http.post<Observable<Object>>(url, {}, options).
            subscribe(principal => {
                this.userName = principal['name'];
            },
            error => {
                if(error.status == 401)
                    alert('Unauthorized');
            }
        );
    }

    logout() {
        sessionStorage.setItem('token', '');
    }
    private handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
          console.error('An error occurred:', error.error.message);
        } else {
          console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);
        }
        return throwError(
          'Something bad happened; please try again later.');
      };
}