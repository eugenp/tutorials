package com.baelding.hexagonal.architecture.model;

public class Cube implements SolidShape {

	private Double length;

	public Cube(Double length) {
		super();
		this.length = length;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}
}
