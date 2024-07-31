package com.baeldung.dagger.intro;

/**
 * Engine of a {@link Car}.
 * 
 * @author Donato Rimenti
 *
 */
public class Engine {

	/**
	 * Starts the engine.
	 */
	public void start() {
		System.out.println("Engine started");
	}

	/**
	 * Stops the engine.
	 */
	public void stop() {
		System.out.println("Engine stopped");
	}
}
