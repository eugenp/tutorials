package com.baeldung.bean;

public class Car {

    private CarEngine engine;

    private Driver driver;

    public Car(CarEngine engine) {
        this.engine = engine;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CarEngine getEngine() {
        return engine;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "Car [engine (volume) =" + engine.getVolume() + ", driver (name) =" + driver.getName() + "]";
    }

}
