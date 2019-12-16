package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MultipleReturnValuesUsingTuplesUnitTest {

    @Test
    public void whenUsingTuple_thenMultipleFieldsAreReturned() {
        
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(10, 12.5, null));
        
        Tuple2<Integer, Coordinates> coordinates = MultipleReturnValuesUsingTuples.getFirstNullNearbyLocation(coordinatesList);
        
        assertEquals(Integer.valueOf(55), coordinates.getFirst());
        assertEquals(10, coordinates.getSecond().getLongitude());
        assertEquals(12.5, coordinates.getSecond().getLatitude());
        assertEquals(null, coordinates.getSecond().getNearbyLocation());
        
    }
    
}
