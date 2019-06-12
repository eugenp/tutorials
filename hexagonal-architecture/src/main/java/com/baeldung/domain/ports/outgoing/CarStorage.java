package com.baeldung.domain.ports.outgoing;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;

import java.util.List;

public interface CarStorage {
    Car addCar(NewCar newCar);
    boolean removeCarById(long carId);
    Car getCarById(long carId);
    List<Car> gatAllCars();
}
