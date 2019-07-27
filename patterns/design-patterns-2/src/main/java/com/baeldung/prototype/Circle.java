package com.baeldung.prototype;

public class Circle extends Shape{
    private int radius;

    public Circle(int radius){
        this.radius = radius;
    }

    public Circle(Circle that){
        this.radius = that.radius;
    }

    @Override
    public Circle clone() {
        return new Circle(this);
    }
}
