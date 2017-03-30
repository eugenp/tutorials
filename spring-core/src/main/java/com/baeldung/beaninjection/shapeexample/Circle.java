package com.baeldung.beaninjection.shapeexample;

/**
 * @author Hasham
 */
public class Circle implements Shape {
    public Circle() {}

    @Override
    public void draw() {
        System.out.println("Drawing a Cirlce");
    }
}
