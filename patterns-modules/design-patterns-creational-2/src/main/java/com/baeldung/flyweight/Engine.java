package com.baeldung.flyweight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Engine for a vehicle.
 * 
 * @author Donato Rimenti
 */
public class Engine {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(Engine.class);

	/**
	 * Starts the engine.
	 */
	public void start() {
		LOG.info("Engine is starting!");
	}

	/**
	 * Stops the engine.
	 */
	public void stop() {
		LOG.info("Engine is stopping!");
	}
}
