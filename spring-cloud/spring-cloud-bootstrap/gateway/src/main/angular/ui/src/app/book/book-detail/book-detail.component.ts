import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Book} from "../../book";
import {Principal} from "../../principal";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  @Input() selectedBook: Book = null;
  @Input() principal: Principal = null;
  @Output() closeBook: EventEmitter<any> = new EventEmitter<any>();


  constructor() { }

  ngOnInit() {
  }

  closeBookDetail() {
    this.closeBook.emit(null);
  }

}
