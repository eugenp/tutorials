package com.baeldung.spring.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Class demonstrating DEPENDENCY INJECTION via CONSTRUCTOR ARGS
 *
 */
public class Car {

	Logger LOGGER = LoggerFactory.getLogger(Car.class);

	public Tyre tyre;

	public Car(Tyre tyre) {
		this.tyre = tyre;
	}

	public void create() {
		LOGGER.info("Creating car with {} tyres and width {}.", tyre.getType(), tyre.getWidth());
	}

}
