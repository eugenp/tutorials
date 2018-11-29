package com.baeldung.hexagonal.architecture.application;

import com.baeldung.hexagonal.architecture.adapters.ShapeCalculatorImpl;
import com.baeldung.hexagonal.architecture.adapters.SolidCalculatorImpl;
import com.baeldung.hexagonal.architecture.core.Circle;
import com.baeldung.hexagonal.architecture.core.Cube;
import com.baeldung.hexagonal.architecture.core.Sphere;
import com.baeldung.hexagonal.architecture.core.Square;
import com.baeldung.hexagonal.architecture.ports.ShapeCalculator;
import com.baeldung.hexagonal.architecture.ports.SolidCalculator;

public class HexagonalArchitectureApplication {

    public static void main(String[] args) {

        ShapeCalculator shapeCalculator = new ShapeCalculatorImpl();
        SolidCalculator solidCalculator = new SolidCalculatorImpl();

        Double radius = 10.0;
        Double sideLength = 10.0;

        Circle circle = new Circle(radius);
        Square square = new Square(sideLength);
        Sphere sphere = new Sphere(radius);
        Cube cube = new Cube(sideLength);

        Double areaOfCircle = shapeCalculator.calculateAreaOfCircle(circle);
        System.out.println("Area of circle is : " + areaOfCircle);

        Double areaOfSquare = shapeCalculator.calculateAreaOfSquare(square);
        System.out.println("Area of square is : " + areaOfSquare);

        Double volumeOfSphere = solidCalculator.calculateVolumeOfSphere(sphere);
        System.out.println("Volume of sphere is : " + volumeOfSphere);

        Double volumeOfCube = solidCalculator.calculateVolumeOfCube(cube);
        System.out.println("Volume of cube is : " + volumeOfCube);

    }
}
