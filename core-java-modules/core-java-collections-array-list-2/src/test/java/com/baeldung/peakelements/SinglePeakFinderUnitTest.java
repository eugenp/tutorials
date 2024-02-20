package com.baeldung.peakelements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SinglePeakFinderUnitTest {

    @Test
    void findSinglePeak_givenArrayOfIntegers_whenValidInput_thenReturnsCorrectPeak() {
        int[] arr = {0, 10, 2, 4, 5, 1};
        assertEquals(10, SinglePeakFinder.findSinglePeak(arr));
    }
}
