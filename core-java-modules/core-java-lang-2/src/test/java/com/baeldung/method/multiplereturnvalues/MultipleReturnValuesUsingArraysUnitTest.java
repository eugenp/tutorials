package com.baeldung.method.multiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class MultipleReturnValuesUsingArraysUnitTest {

    private MultipleReturnValuesUsingArrays multipleReturnValuesUsingArrays;

    @Before
    public void setUp() {
        this.multipleReturnValuesUsingArrays = new MultipleReturnValuesUsingArrays();
    }

    @Test
    public void whenReturningArrayOfObjects_noCompileTimeSafety() {

        Object[] studentData = multipleReturnValuesUsingArrays.getStudentData();

        assertThrows(ClassCastException.class, () -> {
            String name = (String) studentData[1];
        });

    }

    @Test
    public void whenReturningArrayOfObjects2_noCompileTimeSafety() {

        double[] coordinates = multipleReturnValuesUsingArrays.getStreetCoordinates();
        assertEquals(12.5, coordinates[0]);
        assertEquals(10, coordinates[1]);
        /*
        assertThrows(ArrayStoreException.class, () -> {
            multipleReturnValuesUsingArrays.getStudentData2();
        });
        */
    }

    @Test
    public void whenReturningArrayOfObjects3_noCompileTimeSafety() {
        
        assertThrows(ArrayStoreException.class, () -> {
            Number[] coordinates = multipleReturnValuesUsingArrays.getStreetCoordinates2();
        });

    }
    
    @Test
    public void whenReturningArrayOfObjects4_noCompileTimeSafety() {
        
        assertThrows(ClassCastException.class, () -> {
            Object[] coordinates = multipleReturnValuesUsingArrays.getStreetCoordinates3();
            Double longitude = (Double) coordinates[0]; //Throws ClassCastException
        });

    }
}
