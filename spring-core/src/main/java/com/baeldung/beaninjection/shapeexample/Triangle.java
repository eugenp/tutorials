package com.baeldung.beaninjection.shapeexample;

/**
 * @author Hasham
 */
public class Triangle implements Shape {
    public Triangle() {}

    @Override
    public void draw() {
        System.out.println("Drawing a Triangle");
    }
}
