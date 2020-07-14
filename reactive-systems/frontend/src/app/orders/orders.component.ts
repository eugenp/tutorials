import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, FormArray } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrdersBlockingService } from './orders-blocking.service';
import { OrdersReactiveService } from './orders-reactive.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})

export class OrdersComponent implements OnInit {
  form: FormGroup
  response: any
  error: any
  previousOrders: Observable<Object>
  itemList: any
  paymentModes: any

  constructor(public fb: FormBuilder, private http: HttpClient,
    private ordersBlockingService: OrdersBlockingService,
    private ordersReactiveService: OrdersReactiveService) {
      this.paymentModes = this.fetchPaymentModes()
      this.fetchProducts().then(data => this.form = this.createForm());
  }

  ngOnInit() {
      this.response = null
      this.error = null
      this.previousOrders = null
  }

  createForm() {
      let fb = this.fb
      let form = fb.group({
        userId: 'Bob Marley',
        paymentMode: [this.paymentModes[0]],
        lineItems: this.fb.array([]),
        shippingAddress: this.fb.group({
          name: ['Bob Marley'],
          house: ['24'],
          street: ['Ashford Av.'],
          city: ['New York'],
          zip: ['11001']
        })
      })
      let items = this.itemList
      items.forEach(function (value, index) {
        (<FormArray>form.get('lineItems')).push(fb.group({
          'productId':items[index].id,
          'name': items[index].name,
          'stock': items[index].stock,
          'quantity': 10}
        ))
      });
      return form
  }

  async fetchProducts() {
      let products = [
        {"id":"p001", "name": "Product A1", "stock": 101},
        {"id":"p002", "name": "Product A2", "stock": 102}
      ]
      let data  = await this.http.get('http://localhost:8081/api/products').toPromise()
      this.itemList = data
  }

  fetchPaymentModes() {
      let paymentModes = ["Cash on Delivery", "Card on Delivery"]
      return paymentModes
  }

  createOrder() {
      let headers = new HttpHeaders({
        'Content-Type': 'application/json'
      });
      let options = {
        headers: headers
      }
      this.http.post('http://localhost:8080/api/orders', this.form.value, options).subscribe(
        (response) => {
          console.log(response)
          this.error = null
          this.response = response
        },
        (error) => {
          console.log(error)
          this.response = null
          this.error = error
        }
      )
  }

  getOrders() {
      this.previousOrders = this.ordersBlockingService.getOrders()
  }

  getOrderStream() {
      this.previousOrders = this.ordersReactiveService.getOrderStream()
  }

}
