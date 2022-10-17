package com.baeldung.flyweight;

import java.awt.Color;

/**
 * Interface for a vehicle.
 * 
 * @author Donato Rimenti
 */
public interface Vehicle {

	/**
	 * Starts the vehicle.
	 */
	public void start();

	/**
	 * Stops the vehicle.
	 */
	public void stop();

	/**
	 * Gets the color of the vehicle.
	 * 
	 * @return the color of the vehicle
	 */
	public Color getColor();

}