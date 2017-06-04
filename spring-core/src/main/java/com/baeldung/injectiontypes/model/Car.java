package com.baeldung.injectiontypes.model;

public class Car {

    private String brand;
    private String model;

    private Engine engine;

    public Car() {
    }

    public Car(String brand, String model, Engine engine) {
        this.setBrand(brand);
        this.setModel(model);
        this.setEngine(engine);
    }

    @Override
    public String toString() {
        return String.format("Car Brand: %s - Model: %s - %s", this.brand, this.model, this.engine);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
