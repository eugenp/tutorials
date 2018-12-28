package com.baeldung.interfaces.polymorphysim;

public class MainPolymorphic {
    public static void main(String[] args){

        Shape circleShape = new Circle(2);
        Shape squareShape = new Square(2);

        DisplayShape displayShape = new DisplayShape();
        displayShape.add(circleShape);
        displayShape.add(squareShape);

        displayShape.display();
    }
}
