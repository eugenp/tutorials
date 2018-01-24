package com.baeldung.trial;

import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectedBus {

    private Driver driver;

    public ConstructorInjectedBus(Driver driver) {
        this.driver = driver;
    }

    // standard setters and getters
}
