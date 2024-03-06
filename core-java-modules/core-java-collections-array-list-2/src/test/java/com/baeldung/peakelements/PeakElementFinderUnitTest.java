package com.baeldung.peakelements;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PeakElementFinderUnitTest {

    @Test
    void findPeakElement_givenArrayOfIntegers_whenValidInput_thenReturnsCorrectPeak() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {1, 2, 3, 2, 1};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(1, peaks.size());
        assertTrue(peaks.contains(3));
    }

    @Test
    void findPeakElement_givenArrayOfIntegers_whenNoPeaks_thenReturnsEmptyList() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(0, peaks.size());
    }

    @Test
    void findPeakElement_givenArrayOfIntegers_whenPeaksAtExtremes_thenReturnsCorrectPeak() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {5, 2, 1, 3, 4};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(2, peaks.size());
        assertTrue(peaks.contains(5));
        assertTrue(peaks.contains(4));
    }

    @Test
    void findPeakElement_givenArrayOfIntegers_whenPlateaus_thenReturnsCorrectPeak() {
        PeakElementFinder finder = new PeakElementFinder();
        int[] array = {1, 2, 2, 2, 3, 4, 5};
        List<Integer> peaks = finder.findPeakElements(array);
        assertEquals(1, peaks.size());
        assertTrue(peaks.contains(5));
    }
}
