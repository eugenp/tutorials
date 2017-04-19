import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Principal} from "../../principal";
import {Book} from "../../book";

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
  newBook: Book = new Book(null, '', '');
  booksToEdit: number[] = [];
  isAddNewBook: boolean = false;
  selectedBook: Book = null;

  constructor() { }

  ngOnInit() {
    this.loadBooks();
  }

  loadBooks() {
    let book: Book = new Book(1, 'Tom Sawyer', 'Huckleberry Finn');
    let book1: Book = new Book(2, 'Michael Crichton', 'Jurassic Park');
    let book2: Book = new Book(3, 'McLaughlin, Pollice, and West', 'Object Oriented Analysis And Design');
    this.books.push(book, book1, book2);
    this.books.forEach(book => this.newBooks.push(new Book(book.id, book.author, book.title)))
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

  addNewBook(newBook: Book, element: any) {
    //write new book to db
    //on success:
    this.books.push(newBook);
    this.newBooks.push(newBook);
    this.newBook = new Book(null, '', '');
    element.focus();
  }

  cancelAddBook() {
    this.isAddNewBook = false;
  }


  selectBook(book: Book) {
    this.selectedBook = book;
    this.onBookSelected.emit(book);
  }

}
