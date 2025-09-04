package com.baeldung.doublenonvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoubleNonValueUnitTest {
    
    private double[] normalArray;
    private double[] emptyArray;
    private double[] arrayWithNaN;
    private double[] arrayWithInfinite;
    private double[] arrayWithMixedValues;
    
    @BeforeEach
    void setUp() {
        normalArray = new double[]{1.5, 3.2, 2.1, 4.7, 0.8};
        emptyArray = new double[]{};
        arrayWithNaN = new double[]{1.0, Double.NaN, 3.0, Double.NaN, 2.0};
        arrayWithInfinite = new double[]{1.0, Double.POSITIVE_INFINITY, 3.0, Double.NEGATIVE_INFINITY, 2.0};
        arrayWithMixedValues = new double[]{1.0, Double.NaN, 3.0, Double.POSITIVE_INFINITY, 2.0, Double.NEGATIVE_INFINITY};
    }
    
    @Test
    void testFindLargestThrowExceptionWithNormalArray() {
        double result = DoubleNonValue.findLargestThrowException(normalArray);
        assertEquals(4.7, result, 0.001);
    }
    
    @Test
    void testFindLargestThrowExceptionWithEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            DoubleNonValue.findLargestThrowException(emptyArray);
        });
    }
    
    @Test
    void testFindLargestThrowExceptionWithSingleElement() {
        double[] singleElement = {5.0};
        double result = DoubleNonValue.findLargestThrowException(singleElement);
        assertEquals(5.0, result, 0.001);
    }
    
    @Test
    void testFindLargestIgnoreNonValuesWithNormalArray() {
        double result = DoubleNonValue.findLargestIgnoreNonValues(normalArray);
        assertEquals(4.7, result, 0.001);
    }
    
    @Test
    void testFindLargestIgnoreNonValuesWithArrayWithNaN() {
        double result = DoubleNonValue.findLargestIgnoreNonValues(arrayWithNaN);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestIgnoreNonValuesWithArrayWithInfinite() {
        double result = DoubleNonValue.findLargestIgnoreNonValues(arrayWithInfinite);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestIgnoreNonValuesWithArrayWithMixedValues() {
        double result = DoubleNonValue.findLargestIgnoreNonValues(arrayWithMixedValues);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestIgnoreNonValuesWithAllInvalidValues() {
        double[] allInvalid = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        double result = DoubleNonValue.findLargestIgnoreNonValues(allInvalid);
        assertEquals(-1.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNegativeOneWithNormalArray() {
        double result = DoubleNonValue.findLargestReturnNegativeOne(normalArray);
        assertEquals(4.7, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNegativeOneWithEmptyArray() {
        double result = DoubleNonValue.findLargestReturnNegativeOne(emptyArray);
        assertEquals(-1.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNegativeOneWithArrayWithNaN() {
        double result = DoubleNonValue.findLargestReturnNegativeOne(arrayWithNaN);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNegativeOneWithArrayWithInfinite() {
        double result = DoubleNonValue.findLargestReturnNegativeOne(arrayWithInfinite);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNegativeOneWithAllInvalidValues() {
        double[] allInvalid = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        double result = DoubleNonValue.findLargestReturnNegativeOne(allInvalid);
        assertEquals(-1.0, result, 0.001);
    }
    
    @Test
    void testFindLargestWithWrapperWithNormalArray() {
        Double result = DoubleNonValue.findLargestWithWrapper(normalArray);
        assertNotNull(result);
        assertEquals(4.7, result, 0.001);
    }
    
    @Test
    void testFindLargestWithWrapperWithEmptyArray() {
        Double result = DoubleNonValue.findLargestWithWrapper(emptyArray);
        assertNull(result);
    }
    
    @Test
    void testFindLargestWithWrapperWithArrayWithNaN() {
        Double result = DoubleNonValue.findLargestWithWrapper(arrayWithNaN);
        assertNotNull(result);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestWithWrapperWithArrayWithInfinite() {
        Double result = DoubleNonValue.findLargestWithWrapper(arrayWithInfinite);
        assertNotNull(result);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestWithWrapperWithAllInvalidValues() {
        double[] allInvalid = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        Double result = DoubleNonValue.findLargestWithWrapper(allInvalid);
        assertNull(result);
    }
    
    @Test
    void testFindLargestReturnNaNWithNormalArray() {
        double result = DoubleNonValue.findLargestReturnNaN(normalArray);
        assertEquals(4.7, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNaNWithArrayWithNaN() {
        double result = DoubleNonValue.findLargestReturnNaN(arrayWithNaN);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNaNWithArrayWithInfinite() {
        double result = DoubleNonValue.findLargestReturnNaN(arrayWithInfinite);
        assertEquals(3.0, result, 0.001);
    }
    
    @Test
    void testFindLargestReturnNaNWithAllInvalidValues() {
        double[] allInvalid = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        double result = DoubleNonValue.findLargestReturnNaN(allInvalid);
        assertTrue(Double.isNaN(result));
    }
    
    @Test
    void testFindLargestReturnNaNWithEmptyArray() {
        double result = DoubleNonValue.findLargestReturnNaN(emptyArray);
        assertTrue(Double.isNaN(result));
    }
    
    @Test
    void testNaNComparisonBehavior() {
        double nan1 = Double.NaN;
        double nan2 = Double.NaN;
        assertFalse(nan1 == nan2);
        assertTrue(Double.isNaN(nan1));
        assertTrue(Double.isNaN(nan2));
    }
    
    @Test
    void testEdgeCaseWithNegativeValues() {
        double[] negativeArray = {-5.0, -2.0, -10.0, -1.0};
        double result = DoubleNonValue.findLargestThrowException(negativeArray);
        assertEquals(-1.0, result, 0.001);
    }
    
    @Test
    void testEdgeCaseWithZeroValues() {
        double[] zeroArray = {0.0, 0.0, 0.0};
        double result = DoubleNonValue.findLargestThrowException(zeroArray);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    void testEdgeCaseWithMixedPositiveNegative() {
        double[] mixedArray = {-5.0, 10.0, -2.0, 15.0, -1.0};
        double result = DoubleNonValue.findLargestThrowException(mixedArray);
        assertEquals(15.0, result, 0.001);
    }
}
