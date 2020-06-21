package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarPortUserSide;
import com.baeldung.hexagonal.domain.CarService;
import com.baeldung.hexagonal.server.CarServerAdapter;

import static com.baeldung.util.LoggerUtil.LOG;

public class CarUserAdapter {

    public static final String RETRIEVE_CONSTANT = "/all";
    private static CarPortUserSide carPortUserSide = new CarService(new CarServerAdapter());

    private static void submit(Car car) {
        carPortUserSide.submitCar(car);
    }

    private static void retrieve() {
        LOG.info(carPortUserSide.retrieveCars());
    }

    public static void main(String[] args) {
        if (RETRIEVE_CONSTANT.equals(args[0]) && args[1] == null) {
            retrieve();
        } else {
            Car car = new Car(args[0], args[1]);
            submit(car);
        }
    }

}
