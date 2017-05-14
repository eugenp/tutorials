package com.baeldung.model;

/**
 * Model for graphics card.
 */
public class GraphicsCard {
    private String name;

    public GraphicsCard() {
    }

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
