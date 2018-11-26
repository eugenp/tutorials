package com.baelding.hexagonal.architecture.model;

public class Circle implements Shape {

	private Double radius;
	
	public Circle(Double radius) {
		super();
		this.radius = radius;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}
}
