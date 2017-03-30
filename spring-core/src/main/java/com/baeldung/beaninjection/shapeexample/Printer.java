package com.baeldung.beaninjection.shapeexample;

/**
 * @author Hasham
 */
public class Printer {
    private Shape shape;

    // constructor injection
    public Printer(Shape shape) {
        this.shape = shape;
    }

    // setter injection
  /*  public void setShape(Shape shape) {
        this.shape = shape;
    }*/

    public void printShapes() {
        this.shape.draw();
    }
}
