package com.baeldung.infinity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleInfinityUnitTest {
        
    @Test
    void givenInfinities_whenOperatingWithThem_thenNaNOrInfinity() {
        Double positiveInfinity = Double.POSITIVE_INFINITY;
        Double negativeInfinity = Double.NEGATIVE_INFINITY;
        
        assertEquals(Double.NaN, (positiveInfinity + negativeInfinity));
        assertEquals(Double.NaN, (positiveInfinity / negativeInfinity));
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity - negativeInfinity));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity - positiveInfinity));
        assertEquals(Double.NEGATIVE_INFINITY, (positiveInfinity * negativeInfinity));
    }
    
    @Test
    void givenInfinityAndPositiveNumber_whenOperatingWithThem_thenInfinity() {
        Double positiveInfinity = Double.POSITIVE_INFINITY;
        Double negativeInfinity = Double.NEGATIVE_INFINITY;
        double positiveNumber = 10.0; 
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity + positiveNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity + positiveNumber));
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity - positiveNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity - positiveNumber));
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity * positiveNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity * positiveNumber));
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity / positiveNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity / positiveNumber));
        
        assertEquals(Double.NEGATIVE_INFINITY, (positiveNumber - positiveInfinity));
        assertEquals(Double.POSITIVE_INFINITY, (positiveNumber - negativeInfinity));
        
        assertEquals(0.0, (positiveNumber / positiveInfinity));
        assertEquals(-0.0, (positiveNumber / negativeInfinity));
    }
    
    @Test
    void givenInfinityAndBegativeNumber_whenOperatingWithThem_thenInfinity() {
        Double positiveInfinity = Double.POSITIVE_INFINITY;
        Double negativeInfinity = Double.NEGATIVE_INFINITY;
        double negativeNumber = -10.0; 
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity + negativeNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity + negativeNumber));
        
        assertEquals(Double.POSITIVE_INFINITY, (positiveInfinity - negativeNumber));
        assertEquals(Double.NEGATIVE_INFINITY, (negativeInfinity - negativeNumber));
        
        assertEquals(Double.NEGATIVE_INFINITY, (positiveInfinity * negativeNumber));
        assertEquals(Double.POSITIVE_INFINITY, (negativeInfinity * negativeNumber));
        
        assertEquals(Double.NEGATIVE_INFINITY, (positiveInfinity / negativeNumber));
        assertEquals(Double.POSITIVE_INFINITY, (negativeInfinity / negativeNumber));
        
        assertEquals(Double.NEGATIVE_INFINITY, (negativeNumber - positiveInfinity));
        assertEquals(Double.POSITIVE_INFINITY, (negativeNumber - negativeInfinity));
        
        assertEquals(-0.0, (negativeNumber / positiveInfinity));
        assertEquals(0.0, (negativeNumber / negativeInfinity));
    }

    
    @Test
    void givenRealNumbers_whenDivisionByZero_thenInfinity() {
        double d = 1.0;
        
        assertEquals(Double.POSITIVE_INFINITY, (d / 0.0));
        assertEquals(Double.NEGATIVE_INFINITY, (d / -0.0));
        assertEquals(Double.NEGATIVE_INFINITY, (-d / 0.0));
        assertEquals(Double.POSITIVE_INFINITY, (-d / -0.0));
    }
}
