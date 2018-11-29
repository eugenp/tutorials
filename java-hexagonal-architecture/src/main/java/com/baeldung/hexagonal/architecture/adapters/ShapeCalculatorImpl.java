package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.core.Circle;
import com.baeldung.hexagonal.architecture.core.Square;
import com.baeldung.hexagonal.architecture.ports.ShapeCalculator;

public class ShapeCalculatorImpl implements ShapeCalculator {

    public Double calculateAreaOfCircle(Circle circle) {
        Double radius = circle.getRadius();
        return Math.PI * Math.pow(radius, 2);
    }

    public Double calculateAreaOfSquare(Square square) {
        Double sideLength = square.getSideLength();
        return Math.pow(sideLength, 2);
    }
}
