package com.baeldung.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Person {

    private Address address;

    @Autowired
    public Person(Address address) {
        this.address = address;
    }
    
    @Autowired
    public void setAddress(Address address) {
        this.address = address;
    }

}
