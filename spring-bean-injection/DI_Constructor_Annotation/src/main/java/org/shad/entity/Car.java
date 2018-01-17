package org.shad.entity;

public class Car {

	private String name;
	private Engine engine;

	public Car(String name, Engine engine) {
		this.name = name;
		this.engine = engine;
	}

	public void displayDetails() {

		System.out.println("The " + name + " have engine model " + engine.getModelYear() +" via Constructor");
	}

}
