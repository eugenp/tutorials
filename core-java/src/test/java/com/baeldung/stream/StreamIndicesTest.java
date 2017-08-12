package com.baeldung.stream;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.codepoetics.protonpack.Indexed;

public class StreamIndicesTest {

    @Test
    public void givenList_whenGetArrayIndexedStrings_thenReturnListOfEvenIndexedStrings() {
        String[] names = { "Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim" };
        List<String> expectedResult = Arrays.asList("Afrim", "Besim", "Durim");
        List<String> actualResult = StreamIndices.getEvenIndexedStrings(names);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenList_whenGetEvenIndexedStrings_thenReturnListOfEvenIndexedStrings() {
        List<String> names = Arrays.asList("Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim");
        List<Indexed<String>> expectedResult = Arrays.asList(Indexed.index(0, "Afrim"), Indexed.index(2, "Besim"), Indexed.index(4, "Durim"));
        List<Indexed<String>> actualResult = StreamIndices.getEvenIndexedStrings(names);
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
