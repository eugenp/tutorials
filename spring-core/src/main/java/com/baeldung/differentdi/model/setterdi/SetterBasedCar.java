package com.baeldung.differentdi.model.setterdi;

import com.baeldung.differentdi.model.IEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Car class using setter-based dependency injection
 */

@Component
public class SetterBasedCar {
    private IEngine engine;

    @Autowired
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public IEngine getEngine() {
        return engine;
    }
}
