package org.shad.entity;

public class Car {

	private String name;
	private Engine engine;

	public void setName(String name) {
		this.name = name;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void displayDetails() {

		System.out.println("The " + name + " have engine model " + engine.getModelYear());
	}

}
