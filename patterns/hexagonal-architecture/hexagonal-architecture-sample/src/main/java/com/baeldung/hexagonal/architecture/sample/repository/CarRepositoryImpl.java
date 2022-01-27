package com.baeldung.hexagonal.architecture.sample.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.architecture.sample.model.Car;

@Component
public class CarRepositoryImpl implements CarRepository {

	Map<String, Car> cars = new HashMap<>();

	@Override
	public Car getCar(String id) {
		return cars.get(id);
	}

	@Override
	public String getPlate(String id) {
		return cars.get(id).getPlate();
	}

	@Override
	public Car addCar(Car car) {
		cars.put(car.getId(), car);
		return car;
	}

}
