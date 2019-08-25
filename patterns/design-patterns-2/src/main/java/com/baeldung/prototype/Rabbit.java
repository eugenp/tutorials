package com.baeldung.prototype;

public class Rabbit extends Item {
    private String color;
    private String metadata;

    public Rabbit(String color, String metadata) {
        this.color = color;
        this.metadata = metadata;
    }

    public Rabbit(Rabbit that) {
        this.color = that.color;
        this.metadata = that.metadata;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Rabbit clone() {
        return new Rabbit(this);
    }
}
