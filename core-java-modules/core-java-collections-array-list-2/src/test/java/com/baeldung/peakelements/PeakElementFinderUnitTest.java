package com.baeldung.peakelements;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PeakElementFinderUnitTest {

    @Test
    void findPeakElement_givenArrayOfIntegers_whenNoPeaks_thenReturnsMinusOne() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {5, 8, 7, 6, 12};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(2, peaks.size());
    }

    @Test
    void findPeakElement_givenArrayOfIntegers_whenPeaksAtExtremes_thenReturnsCorrectPeakIndex() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {5, 2, 1, 3, 4};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(2, peaks.size());
    }

    @Test
    void findPeakElement_givenArrayOfIntegers_whenPlateaus_thenReturnsCorrectPeakIndex() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {1, 2, 2, 2, 3, 4, 5};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(1, peaks.size());
    }
}
