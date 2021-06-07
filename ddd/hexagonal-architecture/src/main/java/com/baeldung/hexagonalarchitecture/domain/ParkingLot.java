package com.baeldung.hexagonalarchitecture.domain;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingLot {

    @Builder.Default
    private List<Car> cars = new ArrayList<>();
    private ObjectId id;
    private int capacity;

    public boolean isFull() {
        return cars.size() >= capacity;
    }

    public Car park(Car car) {
        if (!isFull()) {
            cars.add(car);
            return car;
        }
        return null;
    }

    public Car unPark(Car car) {
        boolean unParked = cars.removeIf(eachCar -> eachCar.equals(car));
        if (unParked) {
            return car;
        }
        return null;
    }

}
