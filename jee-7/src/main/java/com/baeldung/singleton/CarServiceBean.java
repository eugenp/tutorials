package com.baeldung.singleton;

import java.util.UUID;

import javax.enterprise.context.Dependent;

import org.springframework.web.context.annotation.RequestScope;

@RequestScope
public class CarServiceBean {

    private UUID id = UUID.randomUUID();
    
    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CarService [id=" + id + "]";
    }
    
    public void service(Car car) {
        System.out.println("Car " + car + " is being serviced ...");
        car.setServiced(true);
        System.out.println("Car service for " + car + " is completed.");
    }

}
