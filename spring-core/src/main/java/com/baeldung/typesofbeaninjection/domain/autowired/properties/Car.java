package com.baeldung.typesofbeaninjection.domain.autowired.properties;

import com.baeldung.typesofbeaninjection.domain.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("car-autowired-by-properties")
public class Car {
    private Engine engine;

    public Car() {}

    public Car(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return String.format("Car with %s engine", engine);
    }
}
