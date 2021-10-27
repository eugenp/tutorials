package org.baeldung.hex.ports.inbound;

import org.baeldung.hex.domain.dto.Car;

import java.util.List;

public interface CarService {
    public void addCar(Car car);
    public Car getCar(String vinNumber);
}
