import {Component} from "@angular/core";
import {NgForm} from "@angular/forms";

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

  onLogin(form: NgForm) {
    console.log(form);
  }
}
