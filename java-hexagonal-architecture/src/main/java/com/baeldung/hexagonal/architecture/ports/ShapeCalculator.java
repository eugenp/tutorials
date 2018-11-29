package com.baeldung.hexagonal.architecture.ports;

import com.baeldung.hexagonal.architecture.core.Circle;
import com.baeldung.hexagonal.architecture.core.Square;

public interface ShapeCalculator {

    Double calculateAreaOfCircle(Circle circle);

    Double calculateAreaOfSquare(Square square);
}
