package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.Car;

/**
 * Port of communication with application UI
 */
public interface CarUIPort {

    public void create( Long carId, String brand, String model);

    public Car view(int carId);
}
