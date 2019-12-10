package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.methodmultiplereturnvalues.MultipleReturnValuesUsingArrays;

public class MultipleReturnValuesUsingArraysUnitTest {

    private MultipleReturnValuesUsingArrays multipleReturnValuesUsingArrays;

    @Before
    public void setUp() {
        this.multipleReturnValuesUsingArrays = new MultipleReturnValuesUsingArrays();
    }

    @Test
    public void whenReturningArrayOfDoubles_thenMultipleFieldsAreReturned() {

        double[] coordinates = multipleReturnValuesUsingArrays.getCoordinates();
        assertEquals(10.2, coordinates[0]);
        assertEquals(12.5, coordinates[1]);
    }

    @Test
    public void whenReturningArrayOfNumbers_thenArrayStoreException() {

        assertThrows(ArrayStoreException.class, () -> {
            Number[] coordinates = multipleReturnValuesUsingArrays.getCoordinateNumbers();
        });
        
    }

    @Test
    public void whenReturningArrayOfObjects_thenClassCastException() {
        
        assertThrows(ClassCastException.class, () -> {
            Object[] coordinates = multipleReturnValuesUsingArrays.getCoordinateObjects();
            Double longitude = (Double) coordinates[0];
        });

    }
    
}
