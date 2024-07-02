import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Principal} from "./principal";
import {HttpClientModule, HttpResponse} from "@angular/common/http";
import {Book} from "./book";
import {HttpService} from "./http.service";
import {CommonModule} from '@angular/common';
import {BookDetailComponent} from "./book/book-detail/book-detail.component";
import {BookListComponent} from "./book/book-list/book-list.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,CommonModule, HttpClientModule, BookDetailComponent, BookListComponent],
  providers: [HttpService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'  
})
export class AppComponent {
  selectedBook: Book = null;
  principal: Principal = new Principal(false, []);
  loginFailed: boolean = false;

  constructor(private httpService: HttpService){}

  ngOnInit(): void {
    this.httpService.me()
      .subscribe((response) => {
        let principalJson = response.json();
        this.principal = new Principal(principalJson.authenticated, principalJson.authorities);
      }, (error) => {
        console.log(error);
      });
  }

  onLogout() {
    this.httpService.logout()
      .subscribe((response) => {
        if (response.status === 200) {
          this.loginFailed = false;
          this.principal = new Principal(false, []);
          window.location.replace(response.url);
        }
      }, (error) => {
        console.log(error);
      });
  }

  closeBookDetail() {
    this.selectedBook = null;
  }

  selectBook(book: Book) {
    this.selectedBook = book;
  }

}
