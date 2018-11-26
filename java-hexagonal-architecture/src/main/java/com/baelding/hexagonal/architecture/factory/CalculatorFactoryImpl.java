package com.baelding.hexagonal.architecture.factory;

import com.baelding.hexagonal.architecture.calculator.Calculator;
import com.baelding.hexagonal.architecture.calculator.ShapeCalculatorImpl;
import com.baelding.hexagonal.architecture.calculator.SolidShapeCalculatorImpl;
import com.baelding.hexagonal.architecture.model.Circle;
import com.baelding.hexagonal.architecture.model.Cube;
import com.baelding.hexagonal.architecture.model.GeometricEntity;
import com.baelding.hexagonal.architecture.model.Sphere;
import com.baelding.hexagonal.architecture.model.Square;

public class CalculatorFactoryImpl implements CalculatorFactory {

	public Calculator getCalculator(GeometricEntity geometricEntity) {
		// TODO Auto-generated method stub
		
		if(geometricEntity != null)
		{
			if(geometricEntity instanceof Circle || geometricEntity instanceof Square) {
				return new ShapeCalculatorImpl();
			} else if (geometricEntity instanceof Cube || geometricEntity instanceof Sphere) {
				return new SolidShapeCalculatorImpl();
			}
		}
		return null;
	}
}
