package com.baeldung.prototype;

public class Duck extends Item {
    private String color;
    private String metadata;

    public Duck(String color, String metadata) {
        this.color = color;
        this.metadata = metadata;
    }

    public Duck(Duck that) {
        this.color = that.color;
        this.metadata = that.metadata;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Duck clone() {
        return new Duck(this);
    }
}
