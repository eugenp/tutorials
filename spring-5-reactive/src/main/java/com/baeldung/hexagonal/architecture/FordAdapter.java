package com.baeldung.hexagonal.architecture;

import java.util.List;
import java.util.Map;

public class FordAdapter implements ManufacturingPort {

	@Override
	public void manufacturingMethodology(Car car) {
		// Process for manufacturing ford car
		
	}

	@Override
	public void manufacturingLocation(String location) {
		// Location at which ford manufacturing will take place
		
	}

	@Override
	public void logoForTheCar(Car car) {
		// Put ford logo on the car
		
	}

	@Override
	public void timeToMarketForTheCar(Car car) {
		// Find time to market for a particular ford car model
		
	}

	@Override
	public List<Map<Car, Integer>> totalManufacturingVolume() {
		// Return car production volume of all ford manufacturing units
		return null;
	}

	@Override
	public List<String> listOfAllFactories() {
		// Return list of all ford factories
		return null;
	}
	
	public void fordEngineFuelTest(Car car) {
		//Do engine test for ford
	}

}
