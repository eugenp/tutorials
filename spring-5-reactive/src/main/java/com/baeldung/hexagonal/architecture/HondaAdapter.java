package com.baeldung.hexagonal.architecture;

import java.util.List;
import java.util.Map;

public class HondaAdapter implements ManufacturingPort {

	@Override
	public void manufacturingMethodology(Car car) {
		// Process for manufacturing honda car
		
	}

	@Override
	public void manufacturingLocation(String location) {
		// Location at which honda manufacturing will take place
		
	}

	@Override
	public void logoForTheCar(Car car) {
		// Put honda logo on the car
		
	}

	@Override
	public void timeToMarketForTheCar(Car car) {
		// Find time to market for a particular honda car model
		
	}

	@Override
	public List<Map<Car, Integer>> totalManufacturingVolume() {
		// Return car production volume of all honda manufacturing units
		return null;
	}

	@Override
	public List<String> listOfAllFactories() {
		// Return list of all honda factories
		return null;
	}
	
	public void carCrashAndSafetyTest(Car car) {
		//Do car crash test got honda car
	}

}