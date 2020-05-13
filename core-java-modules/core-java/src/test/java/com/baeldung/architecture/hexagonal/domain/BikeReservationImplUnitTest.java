package com.baeldung.architecture.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class BikeReservationImplUnitTest {

    @Test
    void whenNoBikeAvailable_thenProcessRequestShouldFail() {
        BikeReservationImpl bikeReservation = new BikeReservationImpl();

        // Our data store doesn't contain any tiny tandems!
        BikeReservationRequest request = new BikeReservationRequest(Category.TANDEM, Size.TWELVE);

        try {
            bikeReservation.processRequest(request);

            fail("An exception should have been thrown");
        } catch (NoAvailableBikesException e) {
            assertEquals("No bikes were available in category TANDEM and size TWELVE", e.getMessage());
        }
    }

    @Test
    void whenBikeAvailable_thenRequestProcessesSuccessfully() {
        BikeReservationImpl bikeReservation = new BikeReservationImpl();
        BikeReservationRequest request = new BikeReservationRequest(Category.ELECTRIC, Size.FIFTY);

        try {
            // after reservation has been processed, bike one should be marked as reserved
            Bike expectedBike = new Bike(new BikeId(1), true, Category.ELECTRIC, Size.FIFTY);
            Bike bike = bikeReservation.processRequest(request);
            assertEquals(expectedBike, bike);
        } catch (NoAvailableBikesException e) {
            fail("No exception should have been thrown");
        }
    }
}