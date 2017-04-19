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


  principal: Principal = new Principal(false, []);
  // principal: Principal = new Principal(true, [new Authority("ROLE_ADMIN")]);

  loginFailed: boolean = false;

  booksToEdit: number[] = [];

  newBooks: Book[] = [];
  isAddNewBook: boolean = false;
  newBook: Book = new Book(null, '', '');

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
    this.books.forEach(book => this.newBooks.push(new Book(book.id, book.author, book.title)))
  }

  selectBook(book: Book) {
    this.selectedBook = book;
  }

  closeBookDetail() {
    this.selectedBook = null;
  }

  editBook(bookIndex: number) {
    this.booksToEdit.push(bookIndex);
  }

  cancelEditBook(bookIndex: number) {
    if (this.booksToEdit.indexOf(bookIndex) !== -1) {
      this.booksToEdit.splice(this.booksToEdit.indexOf(bookIndex), 1); //remove the index of the book to edit
      //get the original book
      let bookCopy: Book = new Book(this.books[bookIndex].id, this.books[bookIndex].author, this.books[bookIndex].title);
      this.newBooks.splice(bookIndex,1,bookCopy); //replace the edited book with the old book
    }
  }

  saveBook(bookIndex: number, newBook: Book) {
    console.log(newBook);

    //save the book to the database

    //update the current array of books
    let book: Book = this.books.find(b => b.id === newBook.id);
    book.title = newBook.title;
    book.author = newBook.author;
    this.booksToEdit.splice(this.booksToEdit.indexOf(bookIndex), 1); //remove the index of the book to edit
  }

  delete(bookIndex: number) {
    if (this.selectedBook !== null && this.books[bookIndex].id === this.selectedBook.id) {this.selectedBook = null;}

    this.books.splice(bookIndex, 1); //remove the book at this index;
    this.newBooks.splice(bookIndex, 1); //remove the editing book at this index
  }

  activateAddNewBook() {
    this.isAddNewBook = true;
    this.newBook = new Book(null, '', '');
  }

  cancelAddBook() {
    this.isAddNewBook = false;
  }

  addNewBook(newBook: Book, element: any) {
    //write new book to db
    //on success:
    this.books.push(newBook);
    this.newBooks.push(newBook);
    this.newBook = new Book(null, '', '');
    element.focus();
  }

}
