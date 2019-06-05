package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.CarUIPort;
import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarService;

/**
 * Service adapter between UI and application
 * Implements port for UI communication
 */
public class CarServiceAdapter implements CarUIPort {

    private CarService carService;

    public CarServiceAdapter(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void create(Long carId, String brand, String model) {
        carService.create(carId, brand, model);
    }

    @Override
    public Car view(int carId) {
        return carService.view(carId);
    }
}
