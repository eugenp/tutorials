package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    private Engine engine;
    private Transmission transmission;
    private Trailer trailer;

    public Car() {
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Autowired
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Autowired
    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return String.format("Engine: %s Transmission: %s Trailer: %s", engine, transmission, trailer);
    }
}
