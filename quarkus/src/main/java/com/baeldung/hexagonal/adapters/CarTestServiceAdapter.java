package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.ports.CarUIPort;

import java.util.HashMap;
import java.util.Map;

/**
 * Service adapter only for test purposes
 */
public class CarTestServiceAdapter implements CarUIPort {

    private Map<Long, Car> testCars = new HashMap<>();

    @Override
    public void create(Long carId, String brand, String model) {
        testCars.put(carId, new Car(carId, brand, model));

    }

    @Override
    public Car view(int carId) {
        return testCars.get(carId);
    }
}
