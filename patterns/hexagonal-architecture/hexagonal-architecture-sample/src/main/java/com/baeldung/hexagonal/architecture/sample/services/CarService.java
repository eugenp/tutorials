package com.baeldung.hexagonal.architecture.sample.services;

import com.baeldung.hexagonal.architecture.sample.model.Car;

public interface CarService {

	public Car getCar(String id);

	public String getPlate(String id);

	public Car addCar(Car car);
}
