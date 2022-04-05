package com.baeldung.spring.spel.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("someCar")
public class Car {
    @Value("Some make")
    private String make;
    @Value("Some model")
    private String model;
    @Value("#{engine}")
    private Engine engine;
    @Value("#{engine.horsePower}")
    private int horsePower;
    @Value("2007")
    private int yearOfProduction;
    @Value("#{engine.capacity >= 3000}")
    private boolean isBigEngine;
    @Value("#{someCar.yearOfProduction > 2016}")
    private boolean isNewCar;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public boolean isBigEngine() {
        return isBigEngine;
    }

    public void setBigEngine(boolean bigEngine) {
        isBigEngine = bigEngine;
    }

    public boolean isNewCar() {
        return isNewCar;
    }

    public void setNewCar(boolean newCar) {
        isNewCar = newCar;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", engine=" + engine +
                ", horsePower=" + horsePower +
                ", yearOfProduction=" + yearOfProduction +
                ", isBigEngine=" + isBigEngine +
                ", isNewCar=" + isNewCar +
                '}';
    }
}
