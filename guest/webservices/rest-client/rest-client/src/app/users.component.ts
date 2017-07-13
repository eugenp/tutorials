import { Component } from '@angular/core';
import {UserService, User} from './app.service';

@Component({
  selector: 'users-page',
  providers: [UserService],
  templateUrl: './users.component.html',
  styleUrls: ['./app.component.css']
})
export class UsersComponent {
  title = 'Users';
  
  constructor(
        private _service:UserService){}
     
    public user = {email: "", name: ""}; 	
	public res=[];
 
    addUser() {
      this._service.addUser(this.user)
		.subscribe( () => this.getUsers());
    }
    
    getUsers() {
      this._service.getUsers()
        .subscribe(
          users => {
	        this.res=[];
            users.forEach(usr => {
              this.res.push(
                new User(
                  usr.email,
                  usr.name
                )
              )
            });
          });
    }
}
