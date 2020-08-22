package com.baeldung.partialupdate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String phone;
    //...
    public String phone99;
    
    @Override public String toString() {
        return String.format("Customer %s, Phone: %s", 
                this.name, this.phone);
    }
}
