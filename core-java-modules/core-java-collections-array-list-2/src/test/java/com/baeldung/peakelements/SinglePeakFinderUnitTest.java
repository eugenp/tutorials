package com.baeldung.peakelements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.OptionalInt;

public class SinglePeakFinderUnitTest {

    @Test
    void findSinglePeak_givenArrayOfIntegers_whenValidInput_thenReturnsCorrectPeak() {
        int[] arr = {0, 10, 2, 4, 5, 1};
        OptionalInt peak = SinglePeakFinder.findSinglePeak(arr);
        assertTrue(peak.isPresent());
        assertEquals(10, peak.getAsInt());
    }

    @Test
    void findSinglePeak_givenEmptyArray_thenReturnsEmptyOptional() {
        int[] arr = {};
        OptionalInt peak = SinglePeakFinder.findSinglePeak(arr);
        assertTrue(peak.isEmpty());
    }

    @Test
    void findSinglePeak_givenEqualElementArray_thenReturnsCorrectPeak() {
        int[] arr = {-2, -2, -2, -2, -2};
        OptionalInt peak = SinglePeakFinder.findSinglePeak(arr);
        assertTrue(peak.isPresent());
        assertEquals(-2, peak.getAsInt());
    }
}
