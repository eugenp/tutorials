package com.baeldung.methodmultiplereturnvalues;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipleReturnValuesUsingApacheCommonsPairUnitTest {

    @Test
    void whenUsingPair_thenMultipleFieldsAreReturned() {

        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 1, "home"));
        coordinatesList.add(new Coordinates(2, 2, "school"));
        coordinatesList.add(new Coordinates(3, 3, "hotel"));

        Coordinates target = new Coordinates(5, 5, "gym");

        ImmutablePair<Coordinates, Double> mostDistantPoint = MultipleReturnValuesUsingApacheCommonsPair.getMostDistantPoint(coordinatesList, target);

        assertEquals(1, mostDistantPoint.getLeft().getLongitude());
        assertEquals(1, mostDistantPoint.getLeft().getLatitude());
        assertEquals("home", mostDistantPoint.getLeft().getPlaceName());
        assertEquals(5.66, BigDecimal.valueOf(mostDistantPoint.getRight()).setScale(2, RoundingMode.HALF_UP).doubleValue());

    }

}
