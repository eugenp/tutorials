package com.baeldung.charfreq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

class CharacterWithHighestFrequencyUnitTest {
    private static final String INPUT1 = "aaaaaaaaaa(10) bbbbbbb ccccc dddd eee ff";
    private static final Set<Character> EXPECTED1 = Collections.singleton('a');

    private static final String INPUT2 = "YYYYYYY(7) bbbbb -------(7) dddd eee kkkkkkk(7) ff";
    private static final Set<Character> EXPECTED2 = ImmutableSet.of('Y', '-', 'k');

    @Test
    void whenGettingSingleCharWithHighestFrequencyByStream_shouldSuccess() {
        char result1 = CharacterWithHighestFrequency.byStream(INPUT1);
        assertEquals('a', result1);
    }

    @Test
    void whenGettingCharWithHighestFrequencyByMap_shouldSuccess() {
        Set<Character> result1 = CharacterWithHighestFrequency.byMap(INPUT1);
        assertEquals(EXPECTED1, result1);

        Set<Character> result2 = CharacterWithHighestFrequency.byMap(INPUT2);
        assertEquals(EXPECTED2, result2);

    }

    @Test
    void whenGettingCharWithHighestFrequencyByBucket_shouldSuccess() {
        Set<Character> result1 = CharacterWithHighestFrequency.byBucket(INPUT1);
        assertEquals(EXPECTED1, result1);

        Set<Character> result2 = CharacterWithHighestFrequency.byBucket(INPUT2);
        assertEquals(EXPECTED2, result2);
    }
}