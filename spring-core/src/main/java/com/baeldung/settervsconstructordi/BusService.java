package com.baeldung.settervsconstructordi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates the dependency injection through setter.
 * EngineService is the dependency that is injected through the setter.
 */
@Component(value = "busService")
public class BusService {

    @Autowired
    private EngineService engineService;

    public String start() {
        return engineService.start();
    }
}