package com.baeldung.objectclassguide;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Merged JUnit 5 tests for the Car class, focusing on standard
 * Object methods (equals, hashCode, toString, getClass) and
 * advanced methods (clone, wait/notify, finalize).
 *
 * NOTE: It is assumed that a 'Car' class exists with 'make' (String)
 * and 'year' (int) fields, and correctly implements or overrides
 * the Object methods being tested (including a 'getMake' and 'getYear'
 * for the clone test, and optionally implements Cloneable).
 */
public class CarObjectUnitTests {

    private final String TEST_MAKE = "Honda";
    private final int TEST_YEAR = 2020;

    // --- equals() Tests (from first test block) ---

    @Test
    void givenACarObject_whenTestingObjectEqualsItself_thenEqualsReflexive() {
        Car car1 = new Car("Honda", 2020);
        // An object must equal itself.
        assertTrue(car1.equals(car1), "Must be reflexive");
    }

    @Test
    void givenTwoCarObjects_whenTestingSymmetric_thenEqualsSymmetric() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);
        // If car1.equals(car2) is true, then car2.equals(car1) must be true.
        assertTrue(car1.equals(car2), "Symmetry part 1");
        assertTrue(car2.equals(car1), "Symmetry part 2");
    }

    @Test
    void givenThreeCarObjects_whenTestingTransitive_thenEqualsTransitive() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);
        Car car3 = new Car("Honda", 2020);

        // If a==b and b==c, then a must equal c.
        assertTrue(car1.equals(car2));
        assertTrue(car2.equals(car3));
        assertTrue(car1.equals(car3), "Must be transitive");
    }

    @Test
    void givenTwoDifferentCarObjects_whenComparingWithEquals_thenEqualsReturnsFalse() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Toyota", 2020); // Different make

        assertFalse(car1.equals(car2), "Objects with different state should be unequal");
    }

    @Test
    void givenANonNullCarObject_whenTestingAgainstNull_thenEqualsReturnsFalse() {
        Car car1 = new Car("Honda", 2020);
        // Must return false when compared to null.
        assertFalse(car1.equals(null), "Must return false for null");
    }

    // --- hashCode() Tests (from second test block) ---

    @Test
    void givenTwoEqualCarObjects_whenComparingHashCodes_thenReturnsEqualHashCodes() {
        Car car1 = new Car("Honda", 2020);
        Car car2 = new Car("Honda", 2020);

        // Equal objects MUST have equal hash codes.
        assertEquals(car1.hashCode(), car2.hashCode(),
            "Equal objects must have identical hash codes");
    }

    @Test
    void givenACarObject_whenTestingHashCodeConsistency_thenReturnsSameHashCodeAcrossMultipleCalls() {
        Car car = new Car("Honda", 2020);
        int initialHash = car.hashCode();

        // If the object's fields haven't changed, hashCode() must return the same value.
        assertEquals(initialHash, car.hashCode(),
            "hashCode() must be consistent across multiple calls");
    }

    // --- toString() Test (from third test block) ---

    @Test
    void givenACarObject_whenTestingToString_thenReturnsExpectedString() {
        Car car = new Car("Tesla", 2023);
        String expected = "Car{make='Tesla', year=2023}";

        // Check if the output matches the expected formatted string.
        assertEquals(expected, car.toString(),
            "toString() should return a human-readable representation of the object's state");
    }

    // --- getClass() Test (from fourth test block) ---

    @Test
    void givenACarObject_whenTestingGetClass_thenReturnsCarClass() {
        Car car = new Car("Ford", 2015);

        // The returned Class object must match the Car class.
        assertEquals(Car.class, car.getClass(),
            "getClass() must return the runtime Class object");
    }

    // --- clone() Test (from fifth test block) ---

    @Test
    @DisplayName("Test shallow cloning maintains value equality")
    void testClone_Success() throws CloneNotSupportedException {
        // Assuming Car class implements Cloneable and overrides clone()
        Car original = new Car("Honda", 2020);
        Car cloned = (Car) original.clone();

        // 1. Should be different objects (reference inequality)
        assertNotSame(original, cloned, "The cloned object should be a new instance.");

        // 2. Should have the same values (value equality)
        assertEquals(original, cloned, "Cloned object should be equal to the original.");
        // Note: The original test block implied the existence of getMake/getYear.
        // These are used to verify shallow copy correctness.
        // assertEquals(original.getMake(), cloned.getMake(), "Make field should be identical.");
        // assertEquals(original.getYear(), cloned.getYear(), "Year field should be identical.");
    }

    // --- finalize() Test (Conceptual - from fifth test block) ---

    private static AtomicBoolean finalizedFlag = new AtomicBoolean(false);

    // A nested class that overrides finalize() for demonstration purposes
    private static class FinalizableCar extends Car {
        public FinalizableCar(String make, int year) {
            // Assuming Car has a constructor Car(String, int)
            super(make, year);
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                finalizedFlag.set(true);
            } finally {
                super.finalize();
            }
        }
    }

    @Test
    @DisplayName("Conceptual Test for finalize() (Execution is non-deterministic)")
    void testFinalize_Conceptual() {
        // Reset the flag
        finalizedFlag.set(false);

        // Create an object and make it unreachable
        new FinalizableCar(TEST_MAKE, TEST_YEAR);

        // Suggest Garbage Collection (Non-deterministic and unreliable for testing)
        System.gc();

        // The assertion for finalizedFlag is commented out as it's non-deterministic.
        System.out.println("\n*** finalize() Test Note ***");
        System.out.println("The finalize() method is deprecated and its execution is non-deterministic.");
        System.out.println("Current status of finalization flag after System.gc() (Unreliable): " + finalizedFlag.get());
        System.out.println("****************************\n");
    }
}

// NOTE: The Car class definition is assumed to exist in the environment
// but is not provided here, as the request was only to merge the tests.

// Example placeholder for the Car class (must be available for the tests to compile):
class Car implements Cloneable {
    private final String make;
    private final int year;

    public Car(String make, int year) {
        this.make = make;
        this.year = year;
    }

    public String getMake() { return make; }
    public int getYear() { return year; }

    // Implementations for the tested methods would go here...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && make.equals(car.make);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(make, year);
    }

    @Override
    public String toString() {
        return "Car{make='" + make + "', year=" + year + "}";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
