package com.baelding.hexagonal.architecture.calculator;

import com.baelding.hexagonal.architecture.model.Shape;

public interface ShapeCalculator extends Calculator{

	Double area(Shape shape);
}
