import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers, RequestOptions} from "@angular/http";
import {Book} from "./book";
import {Rating} from "./rating";

@Injectable()
export class HttpService {

  constructor(private http: Http) { }

  login(user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.get("/me", options)
  }

  logout(user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.post("/logout", '', options)
  }

  getBooks(): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.get("/book-service/books", options)
  }

  updateBook(newBook: Book, user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.put("/book-service/books/" + newBook.id, newBook, options)
  }

  deleteBook(book: Book, user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.delete("/book-service/books/" + book.id, options)
  }

  createBook(newBook: Book, user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.post("/book-service/books", newBook, options)
  }

  getRatings(bookId: number, user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.get("/rating-service/ratings?bookId=" + bookId, options)
  }

  createRating(rating: Rating, user: any): Observable<Response> {
    let options = this.makeAuthOptions(user);
    return this.http.post("/rating-service/ratings", rating, options)
  }

  deleteRating(ratingId: number, user: any) {
    let options = this.makeAuthOptions(user);
    return this.http.delete("/rating-service/ratings/" + ratingId, options)
  }

  updateRating(rating: Rating, user: any) {
    let options = this.makeAuthOptions(user);
    return this.http.put("/rating-service/ratings/" + rating.id, rating, options)
  }

  private makeAuthOptions(user: any): RequestOptions {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(user.username + ':' + user.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    return new RequestOptions({headers: headers});;
  }
}
