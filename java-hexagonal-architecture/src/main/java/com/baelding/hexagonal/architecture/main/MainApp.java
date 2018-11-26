package com.baelding.hexagonal.architecture.main;

import com.baelding.hexagonal.architecture.calculator.ShapeCalculator;
import com.baelding.hexagonal.architecture.calculator.SolidShapeCalculator;
import com.baelding.hexagonal.architecture.factory.CalculatorFactory;
import com.baelding.hexagonal.architecture.factory.CalculatorFactoryImpl;
import com.baelding.hexagonal.architecture.model.Circle;
import com.baelding.hexagonal.architecture.model.Cube;
import com.baelding.hexagonal.architecture.model.Shape;
import com.baelding.hexagonal.architecture.model.SolidShape;
import com.baelding.hexagonal.architecture.model.Sphere;
import com.baelding.hexagonal.architecture.model.Square;

public class MainApp {

	public static void main(String[] args) {
		
		CalculatorFactory calculatorFactory = new CalculatorFactoryImpl();
		
		Double radius = 10.0;
		Shape circle = new Circle(radius);
		ShapeCalculator circleShapeCalculator = (ShapeCalculator) calculatorFactory.getCalculator(circle);
		Double circleArea = circleShapeCalculator.area(circle);
		System.out.println("Area of the circle is : " + circleArea);
		
		Double length = 20.0;
		Shape square = new Square(length);
		ShapeCalculator squareShapeCalculator = (ShapeCalculator) calculatorFactory.getCalculator(square);
		Double squareArea = squareShapeCalculator.area(square);
		System.out.println("Area of the square is : " + squareArea);
		
		Double cubeLength = 3.0;
		SolidShape cube = new Cube(cubeLength);
		SolidShapeCalculator cubeSolidShapeCalculator = (SolidShapeCalculator) calculatorFactory.getCalculator(cube);
		Double cubeVolume = cubeSolidShapeCalculator.volume(cube);
		System.out.println("Volume of the cube is : " + cubeVolume);
		
		Double sphereRadius = 5.0;
		SolidShape sphere = new Sphere(sphereRadius);
		SolidShapeCalculator sphereSolidShapeCalculator = (SolidShapeCalculator) calculatorFactory.getCalculator(sphere);
		Double sphereVolume = sphereSolidShapeCalculator.volume(sphere);
		System.out.println("Volume of the sphere is : " + sphereVolume);
		
	}
}
