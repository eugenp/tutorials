package com.baeldung.differentdi.model.constructordi;

import com.baeldung.differentdi.model.IEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Car class using constructor-based dependency injection
 */
@Component
public class ConstructorBasedCar {
    private final IEngine engine;

    @Autowired
    public ConstructorBasedCar(IEngine engine) {
        this.engine = engine;
    }

    public IEngine getEngine() {
        return engine;
    }
}
