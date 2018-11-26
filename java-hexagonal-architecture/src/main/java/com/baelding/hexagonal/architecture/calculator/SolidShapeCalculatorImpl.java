package com.baelding.hexagonal.architecture.calculator;

import com.baelding.hexagonal.architecture.model.Cube;
import com.baelding.hexagonal.architecture.model.SolidShape;
import com.baelding.hexagonal.architecture.model.Sphere;

public class SolidShapeCalculatorImpl implements SolidShapeCalculator {

	public Double volume(SolidShape solidShape) {
		// TODO Auto-generated method stub
		if(solidShape != null) {
			if(solidShape instanceof Sphere) {
				Double radius = ((Sphere) solidShape).getRadius();
				return (Math.PI * Math.pow(radius, 3) * 4)/3;
			} else if (solidShape instanceof Cube) {
				Double length = ((Cube) solidShape).getLength();
				return Math.pow(length, 3);
			}
		}
		return null;
	}

}
