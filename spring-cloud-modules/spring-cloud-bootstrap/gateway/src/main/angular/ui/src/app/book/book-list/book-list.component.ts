import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Principal} from "../../principal";
import {Book} from "../../book";
import {Response} from "@angular/http";
import {HttpService} from "../../http.service";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  @Input() principal: Principal = null;
  @Output() onBookSelected: EventEmitter<Book> = new EventEmitter<Book>();

  books: Book[] = [];
  newBooks: Book[] = [];
  newBook: Book = new Book(Math.floor(Math.random() * 1000), '', '');
  booksToEdit: number[] = [];
  isAddNewBook: boolean = false;
  selectedBook: Book = null;

  constructor(private httpService: HttpService) { }

  ngOnInit() {
    this.loadBooks();
  }

  loadBooks() {
    this.httpService.getBooks()
      .subscribe((response: Response) => {
        let booksJson: any[] = response.json()
        booksJson.forEach(book => {
          this.books.push(new Book(book.id, book.author, book.title));
          this.newBooks.push(new Book(book.id, book.author, book.title));
        })
      }, (error) => {
        console.log(error);
      });
  }

  cancelEditBook(bookIndex: number) {
    if (this.booksToEdit.indexOf(bookIndex) !== -1) {
      this.booksToEdit.splice(this.booksToEdit.indexOf(bookIndex), 1); //remove the index of the book to edit
      //get the original book
      let bookCopy: Book = new Book(this.books[bookIndex].id, this.books[bookIndex].author, this.books[bookIndex].title);
      this.newBooks.splice(bookIndex,1,bookCopy); //replace the edited book with the old book
    }
  }

  editBook(bookIndex: number) {
    this.booksToEdit.push(bookIndex);
  }

  saveBook(bookIndex: number, newBook: Book) {
    console.log(newBook);
    //save the book to the database
    this.httpService.updateBook(newBook)
      .subscribe((response: Response) => {
        let bookJson = response.json();
        let book: Book = new Book(bookJson.id, bookJson.author, bookJson.title);
        //update the current array of books
        let bookArr: Book = this.books.find(b => b.id === book.id);
        bookArr.title = book.title;
        bookArr.author = book.author;
        this.booksToEdit.splice(this.booksToEdit.indexOf(bookIndex), 1); //remove the index of the book to edit
      }, (error) => {
        console.log(error);
      });


  }

  delete(bookIndex: number) {
    let book: Book = this.books[bookIndex];
    this.httpService.deleteBook(book)
      .subscribe(() => {
        if (this.selectedBook !== null && this.books[bookIndex].id === this.selectedBook.id) {
          this.selectedBook = null;
          this.onBookSelected.emit(this.selectedBook);
        }

        this.books.splice(bookIndex, 1); //remove the book at this index;
        this.newBooks.splice(bookIndex, 1); //remove the editing book at this index
      }, (error) => {
        console.log(error);
      });
  }

  activateAddNewBook() {
    this.isAddNewBook = true;
    this.newBook = new Book(null, '', '');
  }

  addNewBook(newBook: Book, element: any) {
    //write new book to db
    this.httpService.createBook(newBook)
      .subscribe((response: Response) => {
        let bookJson = response.json();
        let book: Book = new Book(bookJson.id, bookJson.author, bookJson.title);
        console.log(book);
        this.books.push(book);
        this.newBooks.push(book);
        this.newBook = new Book(Math.floor(Math.random() * 1000), '', '');
        element.focus();
      }, (error) => {
        console.log(error);
      });
  }

  cancelAddBook() {
    this.isAddNewBook = false;
  }


  selectBook(book: Book) {
    this.selectedBook = book;
    this.onBookSelected.emit(book);
  }

}
