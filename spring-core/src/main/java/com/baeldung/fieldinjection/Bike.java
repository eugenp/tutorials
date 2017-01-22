package com.baeldung.fieldinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Transmission;

@Component
public class Bike {
    private Engine engine;
    private Transmission transmission;

    @Autowired
    public Bike(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return String.format("Engine: %s Transmission: %s", engine, transmission);
    }
}
