package com.baeldung.typesofbeaninjection.domain;

public class Car {
    private Engine engine;

    public Car() {}

    public Car(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return String.format("Car with %s engine", engine);
    }
}
