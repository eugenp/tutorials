import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {Book} from "./book";
import {Principal} from "./principal";
import {HttpService} from "./http.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./app.component.html",
  styles: [],
})
export class AppComponent implements OnInit {
  title = 'Book App';
  selectedBook?: Book;
  principal: Principal = new Principal(false, [], {});
  loginFailed: boolean = false;

  constructor(private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.httpService.me().subscribe({
      next: (response) => {
        this.principal = new Principal(response.body?.authenticated || false, response.body?.authorities || [], {})
      },
      complete: () => console.log('complete'),
      error: (error: string) => console.error(error)
    })
  }

  onLogout() {
    this.httpService.logout()
      .subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.loginFailed = false;
            this.principal = new Principal(false, [], {})
            window.location.replace(response.url || "/login");
          }
        },
        error: error => console.error(error),
        complete: () => console.log('complete')
      })
  }
}