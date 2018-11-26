package com.baelding.hexagonal.architecture.model;

public class Square implements Shape {

	private Double length;
	
	public Square(Double length) {
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
