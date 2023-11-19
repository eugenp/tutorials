package com.baeldung.hibernate.pojo.inheritance;

import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle {
    private String engine;

    public Car() {
    }

    public Car(long vehicleId, String manufacturer, String engine) {
        super(vehicleId, manufacturer);
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

}
