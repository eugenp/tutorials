package com.baeldung.trial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjectedBus {

    private Driver driver;

    public Driver getDriver() {
        return driver;
    }

    @Autowired
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
