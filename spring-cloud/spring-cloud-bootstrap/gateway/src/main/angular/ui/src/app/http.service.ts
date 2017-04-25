import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers, RequestOptions} from "@angular/http";
import {Book} from "./book";

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

  getBooks(): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.get("/book-service/books", options)
  }

  updateBook(newBook: Book, user: any): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    return this.http.put("/book-service/books/" + newBook.id, newBook, options)
  }

  deleteBook(book: Book, user: any) {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    return this.http.delete("/book-service/books/" + book.id, options)
  }

  createBook(newBook: Book, user: any) {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    return this.http.post("/book-service/books", newBook, options)
  }
}
