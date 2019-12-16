package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MultipleReturnValuesUsingArraysUnitTest {

    @Test
    public void whenReturningArrayOfDoubles_thenMultipleDoubleFieldsAreReturned() {

        double[] coordinates = MultipleReturnValuesUsingArrays.getCoordinatesDoubleArray();
        assertEquals(10, coordinates[0]);
        assertEquals(12.5, coordinates[1]);
    }

    @Test
    public void whenReturningArrayOfNumbers_thenMultipleNumberFieldsAreReturned() {
        
        Number[] coordinates = MultipleReturnValuesUsingArrays.getCoordinatesNumberArray();
        assertEquals(10, coordinates[0]);
        assertEquals(12.5, coordinates[1]);

    }
    
}
