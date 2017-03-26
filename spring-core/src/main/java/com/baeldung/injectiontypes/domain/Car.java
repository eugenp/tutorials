package com.baeldung.injectiontypes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    private Engine engine;
    private Transmission transmission;
    private MultimediaSystem multimediaSystem;

    // Field Injection
    @Autowired
    TemperatureControlSystem temperatureControlSystem;

    // Constructor injection
    @Autowired
    public Car(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
    }

    // Setter injection
    @Autowired
    public void setMultimediaSystem(MultimediaSystem multimediaSystem) {
        this.multimediaSystem = multimediaSystem;
    }

    @Override
    public String toString() {
        return String.format("Engine: %s\n Transmission: %s\n MultimediaSystem: %s\n TemperatureControlSystem: %s \n", engine, transmission, multimediaSystem, temperatureControlSystem);
    }
}
