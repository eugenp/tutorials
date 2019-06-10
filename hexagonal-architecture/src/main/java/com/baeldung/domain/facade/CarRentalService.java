package com.baeldung.domain.facade;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;
import com.baeldung.domain.ports.exceptions.CarNotFoundException;
import com.baeldung.domain.ports.incoming.BuyCar;
import com.baeldung.domain.ports.incoming.PutUpForSale;
import com.baeldung.domain.ports.incoming.ShowAvailableCars;
import com.baeldung.domain.ports.outgoing.CarStorage;

import java.math.BigDecimal;
import java.util.List;

public class CarRentalService implements ShowAvailableCars, BuyCar, PutUpForSale {

    private CarStorage carStorage;

    public CarRentalService(CarStorage carStorage) {
        this.carStorage = carStorage;
    }

    @Override
    public boolean buyCar(long carId, BigDecimal proposedAmount) {
        Car desiredCar = carStorage.getCarById(carId)
                          .orElseThrow(() -> new CarNotFoundException("Car with given id is not available."));
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
