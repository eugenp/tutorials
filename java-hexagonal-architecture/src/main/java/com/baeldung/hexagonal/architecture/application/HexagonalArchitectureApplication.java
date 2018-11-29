package com.baeldung.hexagonal.architecture.application;

import com.baeldung.hexagonal.architecture.adapters.ShapeCalculatorImpl;
import com.baeldung.hexagonal.architecture.adapters.SolidCalculatorImpl;
import com.baeldung.hexagonal.architecture.ports.ShapeCalculator;
import com.baeldung.hexagonal.architecture.ports.SolidCalculator;

public class HexagonalArchitectureApplication {

    public static void main(String[] args) {

        ShapeCalculator shapeCalculator = new ShapeCalculatorImpl();
        SolidCalculator solidCalculator = new SolidCalculatorImpl();

        Double radius = 10.0;
        Double sideLength = 10.0;

        Double areaOfCircle = shapeCalculator.calculateAreaOfCircle(radius);
        System.out.println("Area of circle is : " + areaOfCircle);

        Double areaOfSquare = shapeCalculator.calculateAreaOfSquare(sideLength);
        System.out.println("Area of square is : " + areaOfSquare);

        Double volumeOfSphere = solidCalculator.calculateVolumeOfSphere(radius);
        System.out.println("Area of sphere is : " + volumeOfSphere);

        Double volumeOfCube = solidCalculator.calculateVolumeOfCube(sideLength);
        System.out.println("Area of cube is : " + volumeOfCube);

    }
}
