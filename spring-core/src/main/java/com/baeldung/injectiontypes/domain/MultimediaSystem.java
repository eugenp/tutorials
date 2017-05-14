package com.baeldung.injectiontypes.domain;

import org.springframework.stereotype.Component;

@Component
public class MultimediaSystem {
    private String make;
    
    public MultimediaSystem(String make) {
        this.make = make;
    }
    
    @Override
    public String toString() {
        return String.format("%s", this.make);
    }
}
