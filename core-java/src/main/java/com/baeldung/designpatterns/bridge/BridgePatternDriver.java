package com.baeldung.designpatterns.bridge;

public class BridgePatternDriver {

    public static void main(String[] args) {
        //a square with red color
        Shape square = new Square(new Red());
        square.drawShape();
        
        //a triangle with blue color
        Shape triangle = new Triangle(new Blue());
        triangle.drawShape();
    }
}
