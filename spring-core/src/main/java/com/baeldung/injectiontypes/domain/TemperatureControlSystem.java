package com.baeldung.injectiontypes.domain;

import org.springframework.stereotype.Component;

@Component
public class TemperatureControlSystem {
    private String make;

    public TemperatureControlSystem(String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return String.format("%s ", this.make);
    }

}
