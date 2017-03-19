package com.baeldung.model;

/**
 * Model for graphics card.
 */
public class GraphicsCard {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GraphicsCard{" +
                "name='" + name + '\'' +
                '}';
    }
}
