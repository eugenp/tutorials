package com.baeldung.serializable_singleton;

public enum EnumSingleton {

    INSTANCE("State Zero");

    private String state;

    private EnumSingleton(String state) {
        this.state = state;
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

    public String getState() { return this.state; }

    public void setState(String state) { this.state = state; }
}