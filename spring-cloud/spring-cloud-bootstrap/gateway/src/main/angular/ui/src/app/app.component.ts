import {Component} from "@angular/core";
import {Principal} from "./principal";
import {Response, RequestOptions, Headers, Http} from "@angular/http";
import {Observable} from "rxjs";
import {NgForm} from "@angular/forms";
import {Book} from "./book";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  credentials = {
    username: '',
    password: ''
  };

  books: Book[] = [];

  selectedBook: Book = null;

  private username: String = '';
  private password: String = '';

  principal: Principal = new Principal(true, []);

  loginFailed: boolean = false;

  constructor(private http: Http){}

  ngOnInit(): void {
    this.loadBooks();
  }

  onLogin(form: NgForm) {
    this.loginFailed = false;
    let headers = new Headers({'Content-Type': 'application/json'});
    this.username = form.value.username;
    this.password = form.value.password;

    headers.append('Authorization','Basic ' + btoa(form.value.username + ':' + form.value.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    this.http.get("/me", options)
      .map((response: Response) => response.json())
      .catch((error: Response) => {
        if (error.status === 401) {
          this.loginFailed = true;
        }
        console.log(error);
        return Observable.throw(error);
      })
      .map((data: any) => new Principal(data.authenticated, data.authorities))
      .subscribe((principal: Principal) => {
        console.log(principal);
        this.principal = principal;
      });
  }

  onLogout() {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(this.username + ':' + this.password));
    headers.append('X-Requested-With','XMLHttpRequest');
    let options = new RequestOptions({headers: headers});
    this.http.post("/logout", '', options)
      .catch((error: Response) => {
        console.log(error);
        return Observable.throw(error);
      })
      .subscribe((response: Response) => {
        if (response.status === 204) {
          this.loginFailed = false;
          this.credentials.username = '';
          this.credentials.password = '';
          this.principal = new Principal(false, []);
        }
      });
  }

  loadBooks() {
    let book: Book = new Book(1, 'Tom Sawyer', 'Huckleberry Finn');
    let book1: Book = new Book(2, 'Michael Crichton', 'Jurassic Park');
    let book2: Book = new Book(3, 'McLaughlin, Pollice, and West', 'Object Oriented Analysis And Design');
    this.books.push(book, book1, book2);
  }

  selectBook(book: Book) {
    this.selectedBook = book;
  }

  closeBookDetail() {
    this.selectedBook = null;
  }

}
