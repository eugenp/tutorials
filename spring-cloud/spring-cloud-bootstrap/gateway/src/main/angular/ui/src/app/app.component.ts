import {Component, OnInit} from "@angular/core";
import {NgForm} from "@angular/forms";
import {RequestOptions, Http, Response, Headers} from "@angular/http";
import "rxjs/Rx";
import {Principal} from "./principal";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  credentials = {
    username: '',
    password: ''
  };

  principal: Principal = new Principal(false, []);

  constructor(private http: Http){}

  ngOnInit(): void {

  }

  onLogin(form: NgForm) {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(form.value.username + ':' + form.value.password));
    let options = new RequestOptions({headers: headers});
    this.http.get("/me", options)
      .map((response: Response) => response.json())
      .map((data: any) => new Principal(data.authenticated, data.authorities))
      .subscribe((principal: Principal) => {
        console.log(principal);
        this.principal = principal;
      });
  }
}
