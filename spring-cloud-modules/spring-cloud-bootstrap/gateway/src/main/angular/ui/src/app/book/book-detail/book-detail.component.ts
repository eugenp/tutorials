import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Book} from "../../book";
import {Principal} from "../../principal";
import {CommonModule} from '@angular/common';
import {HttpService} from "../../http.service";
import {RatingComponent} from "../../rating/rating.component";

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [CommonModule, RatingComponent],
  providers: [HttpService],
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
