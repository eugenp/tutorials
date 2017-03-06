package com.baeldung.differentdi.model.fielddi;

import com.baeldung.differentdi.model.IEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Car class using field-based dependency injection
 */
@Component
public class FieldBasedCar {
    @Autowired
    private IEngine engine;

    public IEngine getEngine() {
        return engine;
    }
}
