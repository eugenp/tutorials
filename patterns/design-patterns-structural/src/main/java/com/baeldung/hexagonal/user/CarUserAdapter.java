package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarPortUserSide;
import com.baeldung.hexagonal.domain.CarService;

import java.util.List;

/**
    This class contains the actual interface to the end user. For example an console application or Rest Controllers with Spring boot.
 */
public class CarUserAdapter implements CarPortUserSide {

    private CarService carService;

    public CarUserAdapter(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void submitCar(Car car) {
        carService.createCar(car);
    }

    @Override
    public List<Car> retrieveCars() {
        return carService.getCars();
    }
}
