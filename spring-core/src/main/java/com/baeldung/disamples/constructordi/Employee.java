package com.baeldung.disamples.constructordi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {
    private Address address;
    public Employee(@Autowired Address address){
        this.address= address;
    }
}
