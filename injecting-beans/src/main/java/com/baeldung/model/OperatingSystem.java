package com.baeldung.model;

/**
 * Model for operating system.
 */
public class OperatingSystem {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OperatingSystem{" +
                "name='" + name + '\'' +
                '}';
    }
}
