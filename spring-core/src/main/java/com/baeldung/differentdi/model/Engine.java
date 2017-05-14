package com.baeldung.differentdi.model;

import org.springframework.stereotype.Component;

/**
 * Implementation of the {com.baeldung.differentdi.model.{@link IEngine}} interface
 */
@Component
public class Engine implements IEngine {
    @Override
    public String getType() {
        return "V8";
    }
}
