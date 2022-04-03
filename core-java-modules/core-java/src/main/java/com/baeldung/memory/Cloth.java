package com.baeldung.memory;

public class Cloth {
    private final String type;
    private String color;

    public Cloth(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
