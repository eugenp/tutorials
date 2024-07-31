package com.baeldung.algorithms.integerstreammedian;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MedianOfIntegerStreamUnitTest {

    @Test
    void givenStreamOfIntegers_whenAnElementIsRead_thenMedianChangesWithApproach1() {
        MedianOfIntegerStream mis = new MedianOfIntegerStream();
        for (Map.Entry<Integer, Double> e : testcaseFixture().entrySet()) {
            mis.add(e.getKey());
            assertEquals(e.getValue(), (Double) mis.getMedian());
        }
    }

    @Test
    void givenStreamOfIntegers_whenAnElementIsRead_thenMedianChangesWithApproach2() {
        MedianOfIntegerStream2 mis = new MedianOfIntegerStream2();
        for (Map.Entry<Integer, Double> e : testcaseFixture().entrySet()) {
            mis.add(e.getKey());
            assertEquals(e.getValue(), (Double) mis.getMedian());
        }
    }

    private Map<Integer, Double> testcaseFixture() {
        return new LinkedHashMap<Integer, Double>() {{
            put(1, 1d);
            put(7, 4d);
            put(5, 5d);
            put(8, 6d);
            put(3, 5d);
            put(9, 6d);
            put(4, 5d);
        }};
    }
}
