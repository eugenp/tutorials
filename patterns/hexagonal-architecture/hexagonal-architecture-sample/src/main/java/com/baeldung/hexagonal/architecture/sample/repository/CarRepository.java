package com.baeldung.hexagonal.architecture.sample.repository;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.architecture.sample.model.Car;

@Component
public interface CarRepository {

	public Car getCar(String id);

	public String getPlate(String id);

	public Car addCar(Car car);

}
