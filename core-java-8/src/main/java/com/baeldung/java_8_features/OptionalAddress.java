package com.baeldung.java_8_features;

import java.util.Optional;

/**
 * Created by Alex Vengrov
 */
public class OptionalAddress {

    private String street;

    public Optional<String> getStreet() {
        return Optional.ofNullable(street);
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
