package com.baeldung.hexagonal.domain;

import java.util.List;

/**
 * This interface is used as a port to the implementation by the user side.
 */
public interface CarPortUserSide {

    void submitCar(Car car);

    List<Car> retrieveCars();

}