package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MultipleReturnValuesUsingTuplesUnitTest {

    @Test
    void whenUsingTuple_thenMultipleFieldsAreReturned() {
        
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 1, "home"));
        coordinatesList.add(new Coordinates(2, 2, "school"));
        coordinatesList.add(new Coordinates(3, 3, "hotel"));
        
        Coordinates target = new Coordinates(5, 5, "gym");
        
        Tuple2<Coordinates, Double> mostDistantPoint = MultipleReturnValuesUsingTuples.getMostDistantPoint(coordinatesList, target);
        
        assertEquals(1, mostDistantPoint.getFirst().getLongitude());
        assertEquals(1, mostDistantPoint.getFirst().getLatitude());
        assertEquals("home", mostDistantPoint.getFirst().getPlaceName());
        assertEquals(5.66, BigDecimal.valueOf(mostDistantPoint.getSecond()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        
    }
    
}
