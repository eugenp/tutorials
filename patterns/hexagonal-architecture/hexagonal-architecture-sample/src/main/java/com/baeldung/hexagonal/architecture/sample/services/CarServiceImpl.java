package com.baeldung.hexagonal.architecture.sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.sample.model.Car;
import com.baeldung.hexagonal.architecture.sample.repository.CarRepositoryImpl;

@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarRepositoryImpl carRepository;

	@Override
	public Car getCar(String id) {
		return carRepository.getCar(id);
	}

	@Override
	public String getPlate(String id) {
		return carRepository.getPlate(id);
	}

	@Override
	public Car addCar(Car car) {
		return carRepository.addCar(car);
	}

	public void setCarRepository(CarRepositoryImpl carRepository) {
		this.carRepository = carRepository;
	}
}
