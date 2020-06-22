package com.baeldung.hexagonalarchitecture.domain;

public class Temperature {

    private final String location;
    private final Integer value;

    public Temperature(String location, Integer value) {
        if (location == null || location.isEmpty())
            throw new IllegalArgumentException("Location is required");

        if (value > 100 || value < -100)
            throw new IllegalArgumentException("Temperature value is too high");

        this.location = location;
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public Integer getValue() {
        return value;
    }

    public String toString() {
        return String.format("Temperature at %s is %d", location, value);
    }
}
