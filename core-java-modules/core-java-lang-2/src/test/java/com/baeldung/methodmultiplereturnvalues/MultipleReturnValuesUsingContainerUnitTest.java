package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MultipleReturnValuesUsingContainerUnitTest {

    @Test
    public void whenUsingContainerClass_thenMultipleFieldsAreReturned() {
        
        Coordinates coordinates = MultipleReturnValuesUsingContainer.getCoordinates();
        
        assertEquals(10, coordinates.getLongitude());
        assertEquals(12.5, coordinates.getLatitude());
        assertEquals(null, coordinates.getNearbyLocation());
    }
}
