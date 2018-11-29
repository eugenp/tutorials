package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.ports.ShapeCalculator;

public class ShapeCalculatorImpl implements ShapeCalculator {

    public Double calculateAreaOfCircle(Double radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    public Double calculateAreaOfSquare(Double sideLength) {
        return Math.pow(sideLength, 2);
    }
}
