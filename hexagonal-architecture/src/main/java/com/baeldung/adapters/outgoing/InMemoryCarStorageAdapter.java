package com.baeldung.adapters.outgoing;

import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;
import com.baeldung.domain.ports.outgoing.CarStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCarStorageAdapter implements CarStorage {
    private Map<Long, Car> availableCars = new ConcurrentHashMap<>();
    private AtomicLong idSequence = new AtomicLong();

    @Override
    public Car addCar(NewCar newCar) {
        Car carToStore = new Car(idSequence.getAndIncrement(), newCar.getName(), newCar.getBrand(), newCar.getMinimumPrice());
        return availableCars.put(carToStore.getCarId(), carToStore);
    }

    @Override
    public boolean removeCarById(long carId) {
        Car removedCar =  availableCars.remove(carId);
        return removedCar != null;
    }

    @Override
    public Optional<Car> getCarById(long carId) {
        return Optional.ofNullable(availableCars.get(carId));
    }

    @Override
    public List<Car> gatAllCars() {
        return new ArrayList<>(availableCars.values());
    }
}
