package com.baeldung.architecture.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BikeUnitTest {

    @Test
    void whenGetIdIsCalled_thenReturnBikeId() {
        Bike bike = new Bike(1, false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getId(), 1);
    }

    @Test
    void whenIsReservedIsCalled_thenReturnReservedFlagValue() {
        Bike bike = new Bike(1, false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());
    }

    @Test
    void whenGetCategoryIsCalled_thenReturnCategory() {
        Bike bike = new Bike(1, false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getCategory(), Category.ELECTRIC);
    }

    @Test
    void whenGetSizeIsCalled_thenReturnSize() {
        Bike bike = new Bike(1, false, Category.ELECTRIC, Size.FIFTY);
        assertEquals(bike.getSize(), Size.FIFTY);
    }

    @Test
    void whenBikeIsNotReserved_thenMarkReservedShouldReserveBike() {
        Bike bike = new Bike(1, false, Category.ELECTRIC, Size.FIFTY);
        assertFalse(bike.isReserved());

        bike.markReserved();
        assertTrue(bike.isReserved());
    }
}