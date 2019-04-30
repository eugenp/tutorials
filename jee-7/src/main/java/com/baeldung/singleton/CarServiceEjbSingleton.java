package com.baeldung.singleton;

import java.util.UUID;

import javax.ejb.Singleton;

@Singleton
public class CarServiceEjbSingleton {

 private UUID id = UUID.randomUUID();
    
    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CarServiceEjbSingleton [id=" + id + "]";
    }
    
    public void service(Car car) {
        System.out.println("Car " + car + " is being serviced ...");
        car.setServiced(true);
        System.out.println("Car service for " + car + " is completed.");
    }
    
}
