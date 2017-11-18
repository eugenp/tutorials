import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers, RequestOptions} from "@angular/http";
import {Book} from "./book";
import {Rating} from "./rating";

@Injectable()
export class HttpService {

  constructor(private http: Http) { }

  me(): Observable<Response> {
    return this.http.get("/me", this.makeOptions())
  }

  logout(): Observable<Response> {
    return this.http.post("/logout", '', this.makeOptions())
  }

  getBooks(): Observable<Response> {
    return this.http.get("/book-service/books", this.makeOptions())
  }

  updateBook(newBook: Book): Observable<Response> {
    return this.http.put("/book-service/books/" + newBook.id, newBook,  this.makeOptions())
  }

  deleteBook(book: Book): Observable<Response> {
    return this.http.delete("/book-service/books/" + book.id,  this.makeOptions())
  }

  createBook(newBook: Book): Observable<Response> {
    return this.http.post("/book-service/books", newBook,  this.makeOptions())
  }

  getRatings(bookId: number): Observable<Response> {
    return this.http.get("/rating-service/ratings?bookId=" + bookId,  this.makeOptions())
  }

  createRating(rating: Rating): Observable<Response> {
    return this.http.post("/rating-service/ratings", rating,  this.makeOptions())
  }

  deleteRating(ratingId: number) {
    return this.http.delete("/rating-service/ratings/" + ratingId,  this.makeOptions())
  }

  updateRating(rating: Rating) {
    return this.http.put("/rating-service/ratings/" + rating.id, rating,  this.makeOptions())
  }

  private makeOptions(): RequestOptions {
    let headers = new Headers({'Content-Type': 'application/json'});
    return new RequestOptions({headers: headers});
  }
}
