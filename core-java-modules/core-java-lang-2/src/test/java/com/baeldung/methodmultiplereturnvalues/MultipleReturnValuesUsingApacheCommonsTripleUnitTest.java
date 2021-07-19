package com.baeldung.methodmultiplereturnvalues;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipleReturnValuesUsingApacheCommonsTripleUnitTest {

    @Test
    void whenUsingTriple_thenMultipleFieldsAreReturned() {

        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 1, "home"));
        coordinatesList.add(new Coordinates(2, 2, "school"));
        coordinatesList.add(new Coordinates(3, 3, "hotel"));

        Coordinates target = new Coordinates(5, 5, "gym");

        ImmutableTriple<Double, Double, Double> minAvgMax = MultipleReturnValuesUsingApacheCommonsTriple.getMinAvgMaxTriple(coordinatesList, target);

        assertEquals(2.83, scaleDouble(minAvgMax.left));   //min
        assertEquals(4.24, scaleDouble(minAvgMax.middle)); //avg
        assertEquals(5.66, scaleDouble(minAvgMax.right));  //max
    }

    private double scaleDouble(Double d) {
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
