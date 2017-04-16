import {Component, OnInit} from "@angular/core";
import {NgForm} from "@angular/forms";
import {RequestOptions, Http, Response, Headers} from "@angular/http";
import "rxjs/Rx";

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

  constructor(private http: Http){}

  ngOnInit(): void {

  }

  onLogin(form: NgForm) {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization','Basic ' + btoa(form.value.username + ':' + form.value.password));
    let options = new RequestOptions({headers: headers});
    this.http.get("/me", options)
      .map((response: Response) => response.json())
      .subscribe((data: any) => {
        console.log(data);
      });
  }
}
