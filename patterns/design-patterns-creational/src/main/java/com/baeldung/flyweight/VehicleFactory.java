package com.baeldung.flyweight;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory which implements the Flyweight pattern to return an existing vehicle
 * if present or a new one otherwise.
 * 
 * @author Donato Rimenti
 */
public class VehicleFactory {

	/**
	 * Stores the already created vehicles.
	 */
	private static Map<Color, Vehicle> vehiclesCache = new HashMap<Color, Vehicle>();

	/**
	 * Private constructor to prevent this class instantiation.
	 */
	private VehicleFactory() {
	}

	/**
	 * Returns a vehicle of the same color passed as argument. If that vehicle
	 * was already created by this factory, that vehicle is returned, otherwise
	 * a new one is created and returned.
	 * 
	 * @param color
	 *            the color of the vehicle to return
	 * @return a vehicle of the specified color
	 */
	public static Vehicle createVehicle(Color color) {
		// Looks for the requested vehicle into the cache.
		// If the vehicle doesn't exist, a new one is created.
		Vehicle newVehicle = vehiclesCache.computeIfAbsent(color, newColor -> {
			// Creates the new car.
			Engine newEngine = new Engine();
			return new Car(newEngine, newColor);
		});
		return newVehicle;
	}
}
