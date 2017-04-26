import {Component} from "@angular/core";
import {Principal} from "./principal";
import {Response} from "@angular/http";
import {NgForm} from "@angular/forms";
import {Book} from "./book";
import {HttpService} from "./http.service";

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
  selectedBook: Book = null;
  principal: Principal = new Principal(false, [], null);
  loginFailed: boolean = false;

  constructor(private httpService: HttpService){}

  ngOnInit(): void {}

  onLogin(form: NgForm) {
    this.loginFailed = false;
    this.credentials = {username: form.value.username, password: form.value.password};
    this.httpService.login(this.credentials)
      .subscribe((response: Response) => {
        let principalJson = response.json();
        this.principal = new Principal(principalJson.authenticated, principalJson.authorities, this.credentials);
      }, (error) => {
        console.log(error);
      });
  }

  onLogout() {
    this.httpService.logout(this.principal.credentials)
      .subscribe((response: Response) => {
        if (response.status === 204) {
          this.loginFailed = false;
          this.credentials.username = '';
          this.credentials.password = '';
          this.principal = new Principal(false, [], null);
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
