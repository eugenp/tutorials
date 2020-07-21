import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';

@Injectable()
export class OrdersBlockingService {

  url: string = 'http://localhost:8080/api/orders'

  constructor(private http: HttpClient) {}

  getOrders() {
    return this.http.get(this.url)
  }

}
