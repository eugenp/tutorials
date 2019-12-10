package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.baeldung.methodmultiplereturnvalues.MultipleReturnValuesUsingCollections;

public class MultipleReturnValuesUsingCollectionsUnitTest {

    private MultipleReturnValuesUsingCollections multipleReturnValuesUsingCollections;

    @Before
    public void setup() {
        this.multipleReturnValuesUsingCollections = new MultipleReturnValuesUsingCollections();
    }

    @Test
    public void whenReturningList_thenMultipleFieldsAreReturned() {
        
        List<Double> coordinates = multipleReturnValuesUsingCollections.getCoordinatesList();
        assertEquals(Double.valueOf(10.2), coordinates.get(0));
        assertEquals(Double.valueOf(12.5), coordinates.get(1));
    }
    
    @Test
    public void whenReturningMap_thenMultipleFieldsAreReturned() {
        
        Map<String, Double> coordinates = multipleReturnValuesUsingCollections.getCoordinatesMap();
        assertEquals(Double.valueOf(10.2), coordinates.get("longitude"));
        assertEquals(Double.valueOf(12.5), coordinates.get("latitude"));
    }
}
