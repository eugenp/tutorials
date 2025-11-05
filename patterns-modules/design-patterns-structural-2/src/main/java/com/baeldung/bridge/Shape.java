package com.baeldung.bridge;

public abstract class Shape {
    protected Color color;
    
    public Shape(Color color) {
        this.color = color;
    }
    
    abstract public String draw();
}
