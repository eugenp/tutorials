package com.baeldung.partialupdate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContactPhone {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(nullable=false)
    public long customerId;
    public String phone;

    @Override
    public String toString() {
       return phone;
    }
}
