package com.baeldung.dagger.intro;

import javax.inject.Inject;

/**
 * Represents a car.
 * 
 * @author Donato Rimenti
 *
 */
public class Car {

	/**
	 * The car's engine.
	 */
	private Engine engine;

	/**
	 * The car's brand.
	 */
	private Brand brand;

	/**
	 * Instantiates a new Car.
	 *
	 * @param engine
	 *            the {@link #engine}
	 * @param brand
	 *            the {@link #brand}
	 */
	@Inject
	public Car(Engine engine, Brand brand) {
		this.engine = engine;
		this.brand = brand;
	}

	/**
	 * Gets the {@link #engine}.
	 *
	 * @return the {@link #engine}
	 */
	public Engine getEngine() {
		return engine;
	}

	/**
	 * Sets the {@link #engine}.
	 *
	 * @param engine
	 *            the new {@link #engine}
	 */
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * Gets the {@link #brand}.
	 *
	 * @return the {@link #brand}
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * Sets the {@link #brand}.
	 *
	 * @param brand
	 *            the new {@link #brand}
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

}
