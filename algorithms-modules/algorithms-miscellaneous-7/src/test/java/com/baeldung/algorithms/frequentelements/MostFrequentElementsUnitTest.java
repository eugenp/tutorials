package com.baeldung.algorithms.frequentelements;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MostFrequentElementsUnitTest {

    private final Integer[] inputArray = {1, 2, 3, 4, 5, 5, 5, 6, 6, 7, 7, 7, 7, 8, 9, 9, 9, 9, 9};
    private final int n = 3;
    private final Integer[] outputArray = {9, 7, 5};

    @Test
    void givenIntegersArray_UseFindByHashMapAndPriorityQueueMethod_thenReturnMostFrequentElements() {
        assertThat(MostFrequentElementsFinder.findByHashMapAndPriorityQueue(inputArray, n)).containsExactly(outputArray);
    }

    @Test
    void givenIntegersArray_UseFindByBucketSortMethod_thenReturnMostFrequentElements() {
        assertThat(MostFrequentElementsFinder.findByBucketSort(inputArray, n)).containsExactly(outputArray);
    }

    @Test
    void givenIntegersArray_UseFindByStreamMethod_thenReturnMostFrequentElements() {
        assertThat(MostFrequentElementsFinder.findByStream(inputArray, n)).containsExactly(outputArray);
    }

    @Test
    void givenIntegersArray_UseFindByTreeMapMethod_thenReturnMostFrequentElements() {
        assertThat(MostFrequentElementsFinder.findByTreeMap(inputArray, n)).containsExactly(outputArray);
    }

}
