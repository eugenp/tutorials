package com.baeldung.injectiontypes.constructorbased.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private String brand;
    private String model;
    private Engine engine;

    @Autowired
    public Car(String brand, String model, Engine engine) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
    }

    @Override
    public String toString() {
        return String.format("Car Brand: %s - Model: %s - %s", this.brand, this.model, this.engine);
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Engine getEngine() {
        return engine;
    }

}
