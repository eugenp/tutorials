import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {RatingComponent} from "./rating/rating.component";
import {ClickStopPropagationDirective} from "./click-stop-propagation.directive";
import {BookDetailComponent} from "./book/book-detail/book-detail.component";
import {BookListComponent} from "./book/book-list/book-list.component";

@NgModule({
  declarations: [
    AppComponent,
    RatingComponent,
    ClickStopPropagationDirective,
    BookDetailComponent,
    BookListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
