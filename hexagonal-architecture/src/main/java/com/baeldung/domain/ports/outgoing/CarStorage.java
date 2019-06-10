package com.baeldung.domain.ports.outgoing;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;

import java.util.List;
import java.util.Optional;

public interface CarStorage {
    Car addCar(NewCar newCar);
    boolean removeCarById(long carId);
    Optional<Car> getCarById(long carId);
    List<Car> gatAllCars();
}
