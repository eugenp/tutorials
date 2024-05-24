package com.baeldung.algorithms.permutation;

import static com.baeldung.algorithms.permutation.CountOccurrence.countOccurrencesWithBinarySearch;
import static com.baeldung.algorithms.permutation.CountOccurrence.countOccurrencesWithCounter;
import static com.baeldung.algorithms.permutation.CountOccurrence.countOccurrencesWithMap;
import static com.baeldung.algorithms.permutation.CountOccurrence.countOccurrencesWithStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CountOccurrenceUnitTest {

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithCounter_thenAtIndexIsCount() {
        int[] counter = countOccurrencesWithCounter(new int[] { 2, 3, 1, 1, 3, 4, 5, 6, 7, 8 });
        assertEquals(2, counter[3]);
    }

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithMap_thenGetIsCount() {
        Map<Integer, Integer> counter = CountOccurrence.countOccurrencesWithMap(new int[] { 2, 3, 1, -1, 3, 4, 5, 6, 7, 8, -1 });
        assertEquals(2, counter.get(-1));
    }

    @Test
    void givenArrayOfString_whenCountOccurrenceWithMap_thenGetIsCount() {
        Map<String, Integer> counter = countOccurrencesWithMap(new String[] { "apple", "orange", "banana", "apple" });
        assertEquals(2, counter.get("apple"));
    }

    @Test
    void givenArrayOfString_whenCountOccurrenceWithStream_thenGetIsCount() {
        Map<String, Long> counter = countOccurrencesWithStream(new String[] { "apple", "orange", "banana", "apple" });
        assertEquals(2, counter.get("apple"));
    }

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithStream_thenGetIsCount() {
        Map<Integer, Long> counter = countOccurrencesWithStream(new int[] { 2, 3, 1, -1, 3, 4, 5, 6, 7, 8, -1 });
        assertEquals(2, counter.get(-1));
    }

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceOfSortedArray_thenReturnOccurrenceWithBinarySearch() {
        int count = countOccurrencesWithBinarySearch(new int[] { 1, 2, 3, 3, 3, 4, 5, 6, 7, 8 }, 10, 3);
        assertEquals(3, count);
    }
}
