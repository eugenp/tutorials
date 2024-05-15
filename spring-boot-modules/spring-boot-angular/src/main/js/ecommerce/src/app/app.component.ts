import {Component} from '@angular/core';
import {EcommerceService} from "./ecommerce/services/EcommerceService";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    providers: [EcommerceService]
})
export class AppComponent {

}
