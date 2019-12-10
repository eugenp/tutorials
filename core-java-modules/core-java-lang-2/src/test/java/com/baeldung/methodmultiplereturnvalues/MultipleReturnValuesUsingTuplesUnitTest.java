package com.baeldung.methodmultiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.methodmultiplereturnvalues.MultipleReturnValuesUsingTuples;
import com.baeldung.methodmultiplereturnvalues.Tuple3;

public class MultipleReturnValuesUsingTuplesUnitTest {

    private MultipleReturnValuesUsingTuples multipleReturnValuesUsingTuples;
    
    @Before
    public void setUp() {
        this.multipleReturnValuesUsingTuples = new MultipleReturnValuesUsingTuples();
    }
    
    @Test
    public void whenUsingTuple_thenMultipleFieldsAreReturned() {
        
        Tuple3<Double, Double, String> coordinates = multipleReturnValuesUsingTuples.getCoordinates();
        
        assertEquals(Double.valueOf(154.2), coordinates.getFirst());
        assertEquals(Double.valueOf(15), coordinates.getSecond());
        assertEquals("directions note", coordinates.getThird());
    }
}
