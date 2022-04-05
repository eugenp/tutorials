import {Component} from "@angular/core";
import {Principal} from "./principal";
import {Response} from "@angular/http";
import {Book} from "./book";
import {HttpService} from "./http.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  selectedBook: Book = null;
  principal: Principal = new Principal(false, []);
  loginFailed: boolean = false;

  constructor(private httpService: HttpService){}

  ngOnInit(): void {
    this.httpService.me()
      .subscribe((response: Response) => {
        let principalJson = response.json();
        this.principal = new Principal(principalJson.authenticated, principalJson.authorities);
      }, (error) => {
        console.log(error);
      });
  }

  onLogout() {
    this.httpService.logout()
      .subscribe((response: Response) => {
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
