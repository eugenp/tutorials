package com.hex.port;

import com.hex.domain.Car;

public interface CarRepositoryPort {

    void create(String model, String make, long color);

    Car getCar(Integer carId);
}