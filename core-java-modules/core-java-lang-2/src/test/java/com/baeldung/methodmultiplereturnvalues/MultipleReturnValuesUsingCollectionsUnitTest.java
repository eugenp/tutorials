package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MultipleReturnValuesUsingCollectionsUnitTest {

    @Test
    public void whenReturningList_thenMultipleFieldsAreReturned() {
        
        List<Number> coordinates = MultipleReturnValuesUsingCollections.getCoordinatesList();
        assertEquals(Integer.valueOf(10), coordinates.get(0));
        assertEquals(Double.valueOf(12.5), coordinates.get(1));
    }
    
    @Test
    public void whenReturningMap_thenMultipleFieldsAreReturned() {
        
        Map<String, Number> coordinates = MultipleReturnValuesUsingCollections.getCoordinatesMap();
        assertEquals(Integer.valueOf(10), coordinates.get("longitude"));
        assertEquals(Double.valueOf(12.5), coordinates.get("latitude"));
    }
}
