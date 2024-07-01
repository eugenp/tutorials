import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {RatingComponent} from "./rating/rating.component";
import {ClickStopPropagationDirective} from "./click-stop-propagation.directive";
import {BookDetailComponent} from "./book/book-detail/book-detail.component";
import {HttpService} from "./http.service";
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    RatingComponent,
    ClickStopPropagationDirective,
    BookDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    CommonModule,
    RouterOutlet
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
