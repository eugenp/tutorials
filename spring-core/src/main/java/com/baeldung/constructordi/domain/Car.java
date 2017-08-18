package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    private Engine engine;
    private Transmission transmission;

    @Autowired
    public Car(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
    }

    // Standard Getters
    public Engine getEngine() {
        return engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    @Override
    public String toString() {
        return String.format("Engine: %s Transmission: %s", engine, transmission);
    }
}
