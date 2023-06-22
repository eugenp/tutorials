package com.baeldung.cloning.deep;

public class Car implements Cloneable {
	private String make;
	private String model;
	private Engine engine;

	public Car(String make, String model, Engine engine) {
		this.make = make;
		this.model = model;
		this.engine = engine;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Engine getEngine() {
		return engine;
	}

	@Override
	public Car clone() {
		try {
			Car clonedCar = (Car) super.clone();
			clonedCar.engine = this.engine.clone();
			return clonedCar;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
