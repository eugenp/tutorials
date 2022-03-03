package com.baeldung.operators.model;

public abstract class Transport {

    private boolean available;

    public abstract String getType();

    public abstract String getName();

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
