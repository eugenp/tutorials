package com.baeldung.settervsconstructordi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates the dependency injection through constructor.
 * EngineService is the dependency that is injected through the constructor.
 */
@Component(value = "carService")
public class CarService {

    EngineService engineService;

    @Autowired
    public CarService(EngineService engineService) {
        this.engineService = engineService;
    }

    public String start() {
        return engineService.start();
    }
}