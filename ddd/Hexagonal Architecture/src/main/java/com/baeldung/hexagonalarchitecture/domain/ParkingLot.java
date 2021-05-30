package com.baeldung.hexagonalarchitecture.domain;

import com.baeldung.hexagonalarchitecture.domain.exception.CarNotFoundException;
import com.baeldung.hexagonalarchitecture.domain.exception.ParkingLotFullException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

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
        if (isFull()) {
            throw new ParkingLotFullException();
        }
        cars.add(car);
        return car;
    }

    public Car unPark(Car car) {
        boolean unParked = cars.removeIf(eachCar -> eachCar.equals(car));
        if (!unParked) {
            throw new CarNotFoundException();
        }
        return car;
    }

}
