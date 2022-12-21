package com.baeldung.models;
public class Car {
    private String model;

    public Car(Car c) {
        this(c.model);
    }

    public Car(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
