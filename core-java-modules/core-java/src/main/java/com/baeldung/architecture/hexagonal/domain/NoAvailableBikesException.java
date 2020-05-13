package com.baeldung.architecture.hexagonal.domain;

public class NoAvailableBikesException extends Exception {

    NoAvailableBikesException(BikeReservationRequest bikeReservationRequest) {
        super("No bikes were available in category " + bikeReservationRequest.getCategory() + " and size " + bikeReservationRequest.getSize());
    }
}
