package com.baeldung.dependencyinjection.model;

public class Keyboard {

    private String keyConfiguration;

    public Keyboard(String keyConfiguration) {
        super();
        this.keyConfiguration = keyConfiguration;
    }

    @Override
    public String toString() {
        return "Keyboard [keyConfiguration=" + keyConfiguration + "]";
    }

    public String getKeyConfiguration() {
        return keyConfiguration;
    }

}
