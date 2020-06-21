package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarPortUserSide;
import com.baeldung.hexagonal.domain.CarService;
import com.baeldung.hexagonal.server.CarServerAdapter;

public class CarUserAdapter {

    private CarPortUserSide carPortUserSide = new CarService(new CarServerAdapter());

    public void submit(Car car) {
        carPortUserSide.submitCar(car);
    }

    public void retrieve() {
        carPortUserSide.retrieveCars();
    }

}
