package com.baeldung.dependencyinjectiontypes.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarWithConstructorInjection {

    private Engine engine;

    @Autowired
    public CarWithConstructorInjection(Engine engine) {
        this.engine = engine;
    }

    public String race() {
        return engine.turnOn();
    }
}
