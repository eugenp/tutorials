package com.baeldung.persistence;

import java.util.List;

import com.baeldung.domain.Car;
import com.baeldung.domain.repostory.CarRepositoryPort;

public class DefaultCarServiceAdapter implements CarRepositoryPort {

	@Override
	public void saveCar(String brand, Integer year) {
	}

	@Override
	public List<Car> listAllCars() {
		return null;
	}

}
