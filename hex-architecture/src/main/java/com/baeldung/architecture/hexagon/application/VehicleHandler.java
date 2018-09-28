package com.baeldung.architecture.hexagon.application;

import java.util.HashMap;
import java.util.Map;
import com.baeldung.architecture.hexagon.domain.IVehicle;


public class VehicleHandler {

	private Map<String, IVehicle> vehicleRegistry;
	private IVehicle car;
	private IVehicle wagon;

	public VehicleHandler() {
		car = new Car();
		wagon = new Wagon();
		vehicleRegistry = new HashMap<>();
		vehicleRegistry.put("car", car);
		vehicleRegistry.put("wagon", wagon);
	}

	public void applyBrake(String vehicle) {
		vehicleRegistry.get(vehicle).brake();
	}
}
