package com.baeldung.architecture.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BikeUnitTest {

    @Test
    void getId() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getId(), new BikeId(1));
    }

    @Test
    void isReserved() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());
    }

    @Test
    void getCategory() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getCategory(), Category.ELECTRIC);
    }

    @Test
    void getSize() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getSize(), Size.FIFTY);
    }

    @Test
    void isMatchingBike() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);

        assertFalse(bike.isMatchingBike(new BikeReservationRequest(Category.ELECTRIC, Size.FIFTYTWO)));
        assertFalse(bike.isMatchingBike(new BikeReservationRequest(Category.TANDEM, Size.FIFTY)));

        assertTrue(bike.isMatchingBike(new BikeReservationRequest(Category.ELECTRIC, Size.FIFTY)));
    }

    @Test
    void reserveBike() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());

        bike.markReserved();
        assertTrue(bike.isReserved());
    }
}