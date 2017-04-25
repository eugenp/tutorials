import {Component} from "@angular/core";
import {Principal} from "./principal";
import {Response} from "@angular/http";
import {Observable} from "rxjs";
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

  private username: String = '';
  private password: String = '';


  principal: Principal = new Principal(false, [], null);
  // principal: Principal = new Principal(true, [new Authority("ROLE_USER")], {username: 'user', password: 'password'});

  loginFailed: boolean = false;

  constructor(private httpService: HttpService){}

  ngOnInit(): void {

  }

  onLogin(form: NgForm) {
    this.loginFailed = false;
    this.credentials = {username: form.value.username, password: form.value.password};
    this.httpService.login(this.credentials)
      .map((response: Response) => response.json())
      .catch((error: Response) => {
        if (error.status === 401) {
          this.loginFailed = true;
        }
        console.log(error);
        return Observable.throw(error);
      })
      .map((data: any) => new Principal(data.authenticated, data.authorities, this.credentials))
      .subscribe((principal: Principal) => {
        console.log(principal);
        this.principal = principal;
      });
  }

  onLogout() {
    this.httpService.logout(this.principal.credentials)
      .catch((error: Response) => {
        console.log(error);
        return Observable.throw(error);
      })
      .subscribe((response: Response) => {
        if (response.status === 204) {
          this.loginFailed = false;
          this.credentials.username = '';
          this.credentials.password = '';
          this.principal = new Principal(false, [], null);
        }
      });
  }

  closeBookDetail() {
    this.selectedBook = null;
  }

  selectBook(book: Book) {
    this.selectedBook = book;
  }

}
