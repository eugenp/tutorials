package com.baeldung.disamples.setterdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {
    private Address address;
    @Autowired
    public void setAddress(Address address){
        this.address= address;
    }
}
