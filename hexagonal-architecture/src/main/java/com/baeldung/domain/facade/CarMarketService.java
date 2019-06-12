package com.baeldung.domain.facade;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;
import com.baeldung.domain.ports.incoming.CarMarketActions;
import com.baeldung.domain.ports.outgoing.CarStorage;

import java.math.BigDecimal;
import java.util.List;

public class CarMarketService implements CarMarketActions {

    private CarStorage carStorage;

    public CarMarketService(CarStorage carStorage) {
        this.carStorage = carStorage;
    }

    @Override
    public boolean buyCar(long carId, BigDecimal proposedAmount) {
        Car desiredCar = carStorage.getCarById(carId);
        if (proposedAmount.compareTo(desiredCar.getMinimumPrice()) >= 0) {
            carStorage.removeCarById(carId);
            return true;
        }
        return false;
    }

    @Override
    public List<Car> showAvailableCars() {
        return carStorage.gatAllCars();
    }

    @Override
    public Car putUpForSale(NewCar newCar) {
        return carStorage.addCar(newCar);
    }
}
