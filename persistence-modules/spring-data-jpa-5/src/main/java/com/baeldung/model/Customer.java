package com.baeldung.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String firstName;
    public String lastName;
    public String address;
    public String email;
    public String phone;
    //...
    public String phone99;
    
    public Customer() {}
    
    @Override public String toString() {
        return String.format("Customer %s %s, Address: %s, Email: %s, Phone: %s", 
                this.firstName, this.lastName, this.address, this.email, this.phone);
    }
}
