package com.baeldung.hibernate.embeddedtable;

import org.hibernate.annotations.EmbeddedTable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SecondaryTable;

@Entity
@SecondaryTable(name = "person_address")
public class Person {
    
    @Id
    private int id;
    
    @Column(name= "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Embedded
    @EmbeddedTable(value = "person_address")
    private Address address;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
