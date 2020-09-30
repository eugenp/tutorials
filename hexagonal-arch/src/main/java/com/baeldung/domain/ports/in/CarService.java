package com.baeldung.domain.ports.in;

import com.baeldung.domain.entities.Car;


public interface CarService {

    Car getCar(String vin);
    Boolean createOrUpdateCar(Car car);
    Boolean deleteCar(String vin);
}
