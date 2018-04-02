package com.baeldung.dependencyinjectiontypes.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarWithSetterInjection {

    private Engine engine;

    public Engine getEngine() {
        return engine;
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String race() {
        return engine.turnOn();
    }
}
