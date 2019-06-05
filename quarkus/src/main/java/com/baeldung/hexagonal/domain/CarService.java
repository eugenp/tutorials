package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.ports.CarUIPort;

/**
 * Main service of our simple application
 */
public class CarService {

    private CarUIPort uiPort;

    public CarService(CarUIPort uiPort) {
        this.uiPort = uiPort;
    }

    public void create(Long carId, String brand, String model) {
        uiPort.create(carId, brand, model);
    }

    public Car view(Integer carId) {
        return uiPort.view(carId);
    }
}