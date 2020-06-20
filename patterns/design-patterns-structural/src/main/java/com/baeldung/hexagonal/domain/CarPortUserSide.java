package com.baeldung.hexagonal.domain;

import java.util.List;

public interface CarPortUserSide {

    void submitCar(Car car);

    List<Car> retrieveCars();

}