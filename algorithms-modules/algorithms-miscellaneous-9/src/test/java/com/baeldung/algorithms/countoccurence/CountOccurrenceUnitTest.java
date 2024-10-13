package com.baeldung.algorithms.countoccurence;

import static com.baeldung.algorithms.countoccurence.CountOccurrence.countOccurrencesWithCounter;
import static com.baeldung.algorithms.countoccurence.CountOccurrence.countOccurrencesWithMap;
import static com.baeldung.algorithms.countoccurence.CountOccurrence.countOccurrencesWithStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CountOccurrenceUnitTest {

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithCounter_thenCounterIndexIsOccurrence() {
        int[] counter = countOccurrencesWithCounter(new int[] { 2, 3, 1, 1, 3, 4, 5, 6, 7, 8 }, 10);
        assertEquals(2, counter[3]);
    }

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithMap_thenMapValueIsOccurrence() {
        Map<Integer, Integer> counter = countOccurrencesWithMap(new Integer[] { 2, 3, 1, -1, 3, 4, 5, 6, 7, 8, -1 });
        assertEquals(2, counter.get(-1));
    }

    @Test
    void givenArrayOfString_whenCountOccurrenceWithMap_thenMapValueIsOccurrence() {
        Map<String, Integer> counter = countOccurrencesWithMap(new String[] { "apple", "orange", "banana", "apple" });
        assertEquals(2, counter.get("apple"));
    }

    @Test
    void givenArrayOfString_whenCountOccurrenceWithStream_thenMapValueIsOccurrence() {
        Map<String, Long> counter = countOccurrencesWithStream(new String[] { "apple", "orange", "banana", "apple" });
        assertEquals(2, counter.get("apple"));
    }

    @Test
    void givenArrayOfIntegers_whenCountOccurrenceWithStream_thenMapValueIsOccurrence() {
        Map<Integer, Long> counter = countOccurrencesWithStream(new Integer[] { 2, 3, 1, -1, 3, 4, 5, 6, 7, 8, -1 });
        assertEquals(2, counter.get(-1));
    }
}
