package com.baeldung.hexagonal.architecture;

import java.util.List;
import java.util.Map;

public interface ManufacturingPort {

	public void manufacturingMethodology(Car car);
	public void manufacturingLocation(String location);
	public void logoForTheCar(Car car);
	public void timeToMarketForTheCar(Car car);
	public List<Map<Car,Integer>> totalManufacturingVolume();
	public List<String> listOfAllFactories();
}
