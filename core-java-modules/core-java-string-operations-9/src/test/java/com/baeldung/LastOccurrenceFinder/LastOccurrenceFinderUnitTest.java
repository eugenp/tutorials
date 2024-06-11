package com.baeldung.LastOccurrenceFinder;

import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class LastOccurrenceFinderUnitTest {
    String str = "Welcome to Baeldung";
    char target = 'e';
    int n = 2;
    int expectedIndex = 6;

    @Test
    public void givenStringAndCharAndN_whenFindingNthLastOccurrence_thenCorrectIndexReturned() {
        int count = 0;
        int index = -1;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == target) {
                count++;
                if (count == n) {
                    index = i;
                    break;
                }
            }
        }
        assertEquals(expectedIndex, index);
    }

    @Test
    public void givenStringAndCharAndN_whenFindingNthLastOccurrenceUsingStreams_thenCorrectIndexReturned() {

        OptionalInt result = IntStream.range(0, str.length())
                .map(i -> str.length() - 1 - i)
                .filter(i -> str.charAt(i) == target)
                .skip(n - 1)
                .findFirst();
        int index = result.orElse(-1);
        assertEquals(expectedIndex, index);
    }
}
