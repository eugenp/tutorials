package com.baeldung.persistence.in_mongo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.domain.Car;
import com.baeldung.domain.repostory.CarRepositoryPort;

@Component
public class MongoDBCarServiceAdapter implements CarRepositoryPort {

	@Override
	public void saveCar(String brand, Integer year) {
	}

	@Override
	public List<Car> listAllCars() {
		return null;
	}

}
