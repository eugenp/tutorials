import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClientModule, HttpResponse, HttpClient, HttpHeaders, HttpRequest, HttpContext} from "@angular/common/http";
import {Book} from "./book";
import {Rating} from "./rating";

@Injectable({providedIn: 'root'})
export class HttpService {

  constructor(private http: HttpClient) { }

  me(): Observable<any> {
    return this.http.get("/me", {'headers': this.makeOptions()})
  }

  logout(): Observable<any> {
    return this.http.post("/logout", '', {'headers': this.makeOptions()})
  }

  getBooks(): Observable<any> {
    return this.http.get("/book-service/books", {'headers': this.makeOptions()})
  }

  updateBook(newBook: Book): Observable<any> {
    return this.http.put("/book-service/books/" + newBook.id, newBook,  {'headers': this.makeOptions()})
  }

  deleteBook(book: Book): Observable<any> {
    return this.http.delete("/book-service/books/" + book.id,  {'headers': this.makeOptions()})
  }

  createBook(newBook: Book): Observable<any> {
    return this.http.post("/book-service/books", newBook,  {'headers': this.makeOptions()})
  }

  getRatings(bookId: number): Observable<any> {
    return this.http.get("/rating-service/ratings?bookId=" + bookId,  {'headers': this.makeOptions()})
  }

  createRating(rating: Rating): Observable<any> {
    return this.http.post("/rating-service/ratings", rating,  {'headers': this.makeOptions()})
  }

  deleteRating(ratingId: number) {
    return this.http.delete("/rating-service/ratings/" + ratingId,  {'headers': this.makeOptions()})
  }

  updateRating(rating: Rating) {
    return this.http.put("/rating-service/ratings/" + rating.id, rating,  {'headers': this.makeOptions()})
  }

  private makeOptions(): HttpHeaders {
    return new HttpHeaders({'Content-Type': 'application/json'});
  }
}
