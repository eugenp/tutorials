package com.baeldung.architecture.hexagonal.port;

import java.util.Collection;

import com.baeldung.architecture.hexagonal.domain.Bike;

public interface BikeDataStorePort {
    Collection<Bike> getAllBikes();

    Bike getBike(int bikeId);

    void updateBike(Bike bike);
}
