package com.baeldung.architecture.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BikeUnitTest {

    @Test
    void whenGetIdIsCalled_thenReturnBikeId() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getId(), new BikeId(1));
    }

    @Test
    void whenIsReservedIsCalled_thenReturnReservedFlagValue() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());
    }

    @Test
    void whenGetCategoryIsCalled_thenReturnCategory() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getCategory(), Category.ELECTRIC);
    }

    @Test
    void whenGetSizeIsCalled_thenReturnSize() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getSize(), Size.FIFTY);
    }

    @Test
    void whenIsMatchingBikeIsCalled_givenNoMatchingBikes_thenReturnFalse() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);

        assertFalse(bike.isMatchingBike(new BikeReservationRequest(Category.ELECTRIC, Size.FIFTYTWO)));
        assertFalse(bike.isMatchingBike(new BikeReservationRequest(Category.TANDEM, Size.FIFTY)));
    }

    @Test
    void whenIsMatchingBikeIsCalled_givenMatchingBikes_thenReturnTrue() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);

        assertTrue(bike.isMatchingBike(new BikeReservationRequest(Category.ELECTRIC, Size.FIFTY)));
    }

    @Test
    void whenBikeIsReserved_thenUpdateIsReservedFlag() {
        Bike bike = new Bike(new BikeId(1), false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());

        bike.markReserved();
        assertTrue(bike.isReserved());
    }
}