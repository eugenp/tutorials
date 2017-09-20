package com.baeldung.beaninjection;

import java.util.Objects;

public class Engine {
    private String capacity;
    private int noOfGears;

    public Engine(String capacity, int noOfGears) {
        this.capacity = capacity;
        this.noOfGears = noOfGears;
    }

    public String getCapacity() {
        return capacity;
    }

    public int getNoOfGears() {
        return noOfGears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return noOfGears == engine.noOfGears &&
                Objects.equals(capacity, engine.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, noOfGears);
    }
}
