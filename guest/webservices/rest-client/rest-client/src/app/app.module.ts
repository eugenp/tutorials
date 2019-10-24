import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { UsersComponent } from './users.component';
import { RouterModule }   from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
	FormsModule,
    RouterModule.forRoot([
      { path: '', component: AppComponent },
      { path: 'users', component: UsersComponent }])],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
