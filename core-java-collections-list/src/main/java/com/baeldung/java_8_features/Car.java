package com.baeldung.java_8_features;

public class Car {

    private String model;
    private int topSpeed;

    public Car(String model, int topSpeed) {
        super();
        this.model = model;
        this.topSpeed = topSpeed;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }


}
