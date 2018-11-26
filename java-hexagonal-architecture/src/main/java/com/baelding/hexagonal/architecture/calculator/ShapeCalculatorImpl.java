package com.baelding.hexagonal.architecture.calculator;

import com.baelding.hexagonal.architecture.model.Circle;
import com.baelding.hexagonal.architecture.model.Shape;
import com.baelding.hexagonal.architecture.model.Square;

public class ShapeCalculatorImpl implements ShapeCalculator {

	public Double area(Shape shape) {
		// TODO Auto-generated method stub
		//return null;
		if(shape != null) {
			if(shape instanceof Circle) {
				Double radius = ((Circle) shape).getRadius();
				return Math.PI * Math.pow(radius, 2);
			} else if (shape instanceof Square) {
				Double length = ((Square) shape).getLength();
				return Math.pow(length, 2);
			}
		}
		return null;
	}

}
