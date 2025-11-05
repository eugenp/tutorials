package com.baeldung.objectclassguide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class CarObjectUnitTests {

    @Test
    void givenACarObject_whenTestingObjectEqualsItself_thenEqualsReflexive() {
        Car car1 = new Car("Honda", 2020);
        assertTrue(car1.equals(car1));
    }

    @Test
    void givenTwoCarObjects_whenTestingSymmetric_thenEqualsSymmetric() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);
        assertTrue(car1.equals(car2));
        assertTrue(car2.equals(car1));
    }

    @Test
    void givenThreeCarObjects_whenTestingTransitive_thenEqualsTransitive() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);
        Car car3 = new Car("Honda", 2020);
        
        assertTrue(car1.equals(car2));
        assertTrue(car2.equals(car3));
        assertTrue(car1.equals(car3));
    }

    @Test
    void givenTwoDifferentCarObjects_whenComparingWithEquals_thenEqualsReturnsFalse() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Toyota", 2020);

        assertFalse(car1.equals(car2));
    }

    @Test
    void givenANonNullCarObject_whenTestingAgainstNull_thenEqualsReturnsFalse() {
        Car car1 = new Car("Honda", 2020);
        
        assertFalse(car1.equals(null));
    }

    @Test
    void givenTwoEqualCarObjects_whenComparingHashCodes_thenReturnsEqualHashCodes() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);
        
        assertEquals(car1.hashCode(), car2.hashCode());
    }

    @Test
    void givenACarObject_whenTestingHashCodeConsistency_thenReturnsSameHashCodeAcrossMultipleCalls() {
        Car car = new Car("Honda", 2020);
        int initialHash = car.hashCode();
        
        assertEquals(initialHash, car.hashCode());
    }

    @Test
    void givenACarObject_whenTestingToString_thenReturnsExpectedString() {
        Car car = new Car("Tesla", 2023);
        String expected = "Car{make='Tesla', year=2023}";

        assertEquals(expected, car.toString());
    }

    @Test
    void givenACarObject_whenTestingGetClass_thenReturnsCarClass() {
        Car car = new Car("Ford", 2015);
        
        assertEquals(Car.class, car.getClass());
    }

    @Test
    void givenACarObject_whenTestingClone_thenCloneSuccess() throws CloneNotSupportedException {
        Car original = new Car("Honda", 2020);
        Car cloned = (Car) original.clone();
        
        assertNotSame(original, cloned);
        assertEquals(original, cloned);
        assertEquals(original.getMake(), cloned.getMake()); 
        assertEquals(original.getYear(), cloned.getYear());
    }
}
