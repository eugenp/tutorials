package com.baeldung.hexagonal.architecture.ports;

public interface ShapeCalculator {

    Double calculateAreaOfCircle(Double radius);

    Double calculateAreaOfSquare(Double sideLength);
}
