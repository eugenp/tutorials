package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.methodmultiplereturnvalues.Coordinates;
import com.baeldung.methodmultiplereturnvalues.MultipleReturnValuesUsingContainer;

public class MultipleReturnValuesUsingContainerUnitTest {

    private MultipleReturnValuesUsingContainer multipleReturnValuesUsingContainer;
    
    @Before
    public void setup() {
        
        this.multipleReturnValuesUsingContainer = new MultipleReturnValuesUsingContainer();
    }
    
    @Test
    public void whenUsingContainerClass_thenMultipleFieldsAreReturned() {
        
        Coordinates coordinates = multipleReturnValuesUsingContainer.getCoordinates();
        
        assertEquals(154.2, coordinates.getLongitude());
        assertEquals(15, coordinates.getLatitude());
        assertEquals("directions note", coordinates.getDirectionsNote());
    }
}
