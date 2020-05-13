package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.BikeReservationRequest;
import com.baeldung.architecture.hexagonal.domain.NoAvailableBikesException;

public interface BikeReservationPort {
    Bike processRequest(BikeReservationRequest bikeReservationRequest) throws NoAvailableBikesException;
}
