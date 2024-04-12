package com.baeldung.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@DependsOn("engine")
public class Car implements Vehicle {

    @Autowired
    private Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

}
