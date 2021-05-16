import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { OrdersComponent } from './orders/orders.component';

import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { OrdersBlockingService } from './orders/orders-blocking.service';
import { OrdersReactiveService } from './orders/orders-reactive.service';

@NgModule({
  declarations: [
    AppComponent,
    OrdersComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    OrdersBlockingService,
    OrdersReactiveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
