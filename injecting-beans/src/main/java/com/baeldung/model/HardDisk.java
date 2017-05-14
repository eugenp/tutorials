package com.baeldung.model;

/**
 * Model for hard disk drive.
 */
public class HardDisk {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HardDisk{" +
                "name='" + name + '\'' +
                '}';
    }
}
