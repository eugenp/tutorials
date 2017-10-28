package com.baeldung.spring.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Class demonstrating DEPENDENCY INJECTION via SETTER
 *
 */
public class Truck {

	Logger LOGGER = LoggerFactory.getLogger(Truck.class);

	public Tyre tyre;

	public void setTyre(Tyre tyre) {
		this.tyre = tyre;
	}

	public void create() {
		LOGGER.info("Creating truck with {} tyres and width {}.", tyre.getType(), tyre.getWidth());
	}

}
