package com.baeldung.doublenonvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DoubleNonValuesUnitTest {

    @Test
    public void testDemonstrateNaNBehavior() {
        double nan1 = Double.NaN;
        double nan2 = Double.NaN;
        
        // Test that NaN == NaN is always false
        assertFalse(nan1 == nan2);
        
        // Test that Double.isNaN() correctly identifies NaN values
        assertTrue(Double.isNaN(nan1));
        assertTrue(Double.isNaN(nan2));
        
        // Test that the method returns the expected string
        String result = DoubleNonValues.demonstrateNaNBehavior();
        assertTrue(result.contains("NaN comparison behavior:"));
        assertTrue(result.contains("Double.NaN == Double.NaN: false"));
        assertTrue(result.contains("Double.isNaN(Double.NaN): true"));
    }

    @Test
    public void testFindLargestThrowException() {
        double[] validArray = {1.0, 5.0, 3.0, 9.0, 2.0};
        assertEquals(9.0, DoubleNonValues.findLargestThrowException(validArray));
        
        // Test that it throws exception for empty array
        assertThrows(IllegalArgumentException.class, () -> {
            DoubleNonValues.findLargestThrowException(new double[]{});
        });
    }

    @Test
    public void testFindLargestIgnoreNonValues() {
        double[] arrayWithNaN = {1.0, Double.NaN, 5.0, Double.POSITIVE_INFINITY, 3.0};
        assertEquals(5.0, DoubleNonValues.findLargestIgnoreNonValues(arrayWithNaN));
        
        double[] allInvalidArray = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        assertEquals(-1.0, DoubleNonValues.findLargestIgnoreNonValues(allInvalidArray));
    }

    @Test
    public void testFindLargestReturnNegativeOne() {
        double[] arrayWithNaN = {1.0, Double.NaN, 5.0, Double.POSITIVE_INFINITY, 3.0};
        assertEquals(5.0, DoubleNonValues.findLargestReturnNegativeOne(arrayWithNaN));
        
        double[] allInvalidArray = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        assertEquals(-1.0, DoubleNonValues.findLargestReturnNegativeOne(allInvalidArray));
        
        // Test empty array returns -1.0
        assertEquals(-1.0, DoubleNonValues.findLargestReturnNegativeOne(new double[]{}));
    }

    @Test
    public void testFindLargestWithWrapper() {
        double[] arrayWithNaN = {1.0, Double.NaN, 5.0, Double.POSITIVE_INFINITY, 3.0};
        assertEquals(5.0, DoubleNonValues.findLargestWithWrapper(arrayWithNaN));
        
        double[] allInvalidArray = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        assertNull(DoubleNonValues.findLargestWithWrapper(allInvalidArray));
    }

    @Test
    public void testFindLargestReturnNaN() {
        double[] arrayWithNaN = {1.0, Double.NaN, 5.0, Double.POSITIVE_INFINITY, 3.0};
        assertEquals(5.0, DoubleNonValues.findLargestReturnNaN(arrayWithNaN));
        
        double[] allInvalidArray = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        assertTrue(Double.isNaN(DoubleNonValues.findLargestReturnNaN(allInvalidArray)));
    }
}
