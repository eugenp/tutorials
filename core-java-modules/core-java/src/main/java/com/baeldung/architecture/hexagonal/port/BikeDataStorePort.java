package com.baeldung.architecture.hexagonal.port;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.Bike;

public interface BikeDataStorePort {
    List<Bike> getAllBikes();

    void updateBike(Bike bike);
}
