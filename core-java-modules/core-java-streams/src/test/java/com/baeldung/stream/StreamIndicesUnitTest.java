package com.baeldung.stream;

import com.codepoetics.protonpack.Indexed;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StreamIndicesUnitTest {

    @Test
    public void whenCalled_thenReturnListOfEvenIndexedStrings() {
        String[] names = {"Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim"};
        List<String> expectedResult = Arrays.asList("Afrim", "Besim", "Durim");
        List<String> actualResult = StreamIndices.getEvenIndexedStrings(names);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCalled_thenReturnListOfEvenIndexedStringsVersionTwo() {
        String[] names = {"Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim"};
        List<String> expectedResult = Arrays.asList("Afrim", "Besim", "Durim");
        List<String> actualResult = StreamIndices.getEvenIndexedStrings(names);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCalled_thenReturnListOfOddStrings() {
        String[] names = {"Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim"};
        List<String> expectedResult = Arrays.asList("Bashkim", "Lulzim", "Shpetim");
        List<String> actualResult = StreamIndices.getOddIndexedStrings(names);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenList_whenCalled_thenReturnListOfEvenIndexedStrings() {
        List<String> names = Arrays.asList("Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim");
        List<Indexed<String>> expectedResult = Arrays
          .asList(Indexed.index(0, "Afrim"), Indexed.index(2, "Besim"), Indexed
            .index(4, "Durim"));
        List<Indexed<String>> actualResult = StreamIndices.getEvenIndexedStrings(names);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenList_whenCalled_thenReturnListOfOddIndexedStrings() {
        List<String> names = Arrays.asList("Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim");
        List<Indexed<String>> expectedResult = Arrays
          .asList(Indexed.index(1, "Bashkim"), Indexed.index(3, "Lulzim"), Indexed
            .index(5, "Shpetim"));
        List<Indexed<String>> actualResult = StreamIndices.getOddIndexedStrings(names);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCalled_thenReturnListOfOddStringsVersionTwo() {
        String[] names = {"Afrim", "Bashkim", "Besim", "Lulzim", "Durim", "Shpetim"};
        List<String> expectedResult = Arrays.asList("Bashkim", "Lulzim", "Shpetim");
        List<String> actualResult = StreamIndices.getOddIndexedStringsVersionTwo(names);

        assertEquals(expectedResult, actualResult);
    }
}