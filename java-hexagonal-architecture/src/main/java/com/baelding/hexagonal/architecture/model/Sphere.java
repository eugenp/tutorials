package com.baelding.hexagonal.architecture.model;

public class Sphere implements SolidShape {

	private Double radius;
	
	public Sphere(Double radius) {
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
