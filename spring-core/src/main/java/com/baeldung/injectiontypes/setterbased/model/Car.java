package com.baeldung.injectiontypes.setterbased.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    @Autowired
    private String brand;

    @Autowired
    private String model;

    @Autowired
    private Engine engine;

    public Car() {
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
