package com.baeldung.architecture.hexagonal.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BikeReservationImplUnitTest {

    @Test
    void whenNoBikeAvailable_thenProcessingReservationRequestShouldFail() {
        BikeReservationImpl bikeReservation = new BikeReservationImpl();
        try {
            // Our data store doesn't contain any tiny tandems!
            bikeReservation.processReservationRequest(Category.TANDEM, Size.TWELVE);

            fail("When no bikes match the request then it should fail.");
        } catch (Throwable e) {
            assertEquals("No bikes were available in category TANDEM and size TWELVE", e.getMessage());
        }
    }

    @Test
    void whenBikeAvailable_thenProcessingReservationRequestShouldSucceed() {
        BikeReservationImpl bikeReservation = new BikeReservationImpl();

        // after reservation has been processed, bike one should be marked as reserved
        Bike expectedBike = new Bike(1, true, Category.ELECTRIC, Size.FIFTY);
        Bike bike = bikeReservation.processReservationRequest(Category.ELECTRIC, Size.FIFTY);
        assertEquals(expectedBike, bike);
    }
}