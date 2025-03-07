package com.baeldung.streams;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class NestedLoopsToStreamsConverterUnitTest {

    @Test
    public void givenTwoLists_whenGeneratingAllPairs_thenResultsShouldMatch() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<int[]> imperativeResult = NestedLoopsToStreamsConverter.getAllPairsImperative(list1, list2);
        List<int[]> streamResult = NestedLoopsToStreamsConverter.getAllPairsStream(list1, list2);
        assertEquals(imperativeResult.size(), streamResult.size());
        for (int i = 0; i < imperativeResult.size(); i++) {
            assertArrayEquals(imperativeResult.get(i), streamResult.get(i));
        }
    }

    @Test
    public void givenTwoLists_whenFilteringPairsBySum_thenResultsShouldMatch() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<int[]> imperativeResult = NestedLoopsToStreamsConverter.getFilteredPairsImperative(list1, list2);
        List<int[]> streamResult = NestedLoopsToStreamsConverter.getFilteredPairsStream(list1, list2);
        assertEquals(imperativeResult.size(), streamResult.size());
        for (int i = 0; i < imperativeResult.size(); i++) {
            assertArrayEquals(imperativeResult.get(i), streamResult.get(i));
        }
    }

    @Test
    public void givenTwoLists_whenFindingFirstMatchingPair_thenResultsShouldMatch() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        Optional<int[]> imperativeResult = NestedLoopsToStreamsConverter.getFirstMatchingPairImperative(list1, list2);
        Optional<int[]> streamResult = NestedLoopsToStreamsConverter.getFirstMatchingPairStream(list1, list2);
        assertEquals(imperativeResult.isPresent(), streamResult.isPresent());
        imperativeResult.ifPresent(pair -> assertArrayEquals(pair, streamResult.get()));
    }

}
