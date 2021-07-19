package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MultipleReturnValuesUsingArraysUnitTest {

    @Test
    void whenUsingArrayOfDoubles_thenMultipleDoubleFieldsAreReturned() {

        double[] coordinates = MultipleReturnValuesUsingArrays.getCoordinatesDoubleArray();
        assertEquals(10, coordinates[0]);
        assertEquals(12.5, coordinates[1]);
    }

    @Test
    void whenUsingArrayOfNumbers_thenMultipleNumberFieldsAreReturned() {
        
        Number[] coordinates = MultipleReturnValuesUsingArrays.getCoordinatesNumberArray();
        assertEquals(10, coordinates[0]);
        assertEquals(12.5, coordinates[1]);

    }
    
}
