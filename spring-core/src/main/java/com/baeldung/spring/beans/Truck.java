package com.baeldung.spring.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Class demonstrating DEPENDENCY INJECTION via SETTER
 *
 */
public class Truck {

	Logger LOGGER = LoggerFactory.getLogger(Truck.class);

	@Autowired
	private Tyre tyre;

	public void create() {
		LOGGER.info("Creating truck with {} tyres and width {}.", tyre.getType(), tyre.getWidth());
	}

	public Tyre getTyre() {
		return tyre;
	}
	
}
