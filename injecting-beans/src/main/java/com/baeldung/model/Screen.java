package com.baeldung.model;

/**
 * Model for Screen.
 */
public class Screen {

    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "size='" + size + '\'' +
                '}';
    }
}
