package com.baeldung.stream;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StreamIndicesTest {
    @Test
    public void givenList_whenGetEvenIndexedStrings_thenReturnListOfStrings() {
        String[] names = { "Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim" };
        List<String> expectedResult = Arrays.asList("Afrim", "Besim", "Durim");
        List<String> actualResult = StreamIndices.getEvenIndexedStrings(names);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenList_whenGetOddIndexedStrings_thenReturnListOfStrings() {
        String[] names = { "Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim" };
        List<String> expectedResult = Arrays.asList("Bashkim", "Lulzim", "Shpetim");
        List<String> actualResult = StreamIndices.getOddIndexedStrings(names);
        assertEquals(expectedResult, actualResult);
    }
}
