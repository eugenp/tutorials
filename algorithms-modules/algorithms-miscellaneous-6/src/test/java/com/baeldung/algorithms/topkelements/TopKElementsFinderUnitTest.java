package com.baeldung.algorithms.topkelements;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TopKElementsFinderUnitTest {
    private final TopKElementsFinder<Integer> bruteForceFinder = new BruteForceTopKElementsFinder();
    private final TopKElementsFinder<Integer> maxHeapFinder = new MaxHeapTopKElementsFinder();
    private final TopKElementsFinder<Integer> treeSetFinder = new TreeSetTopKElementsFinder();

    private final int k = 4;
    private final List<Integer> distinctIntegers = Arrays.asList(1, 2, 3, 9, 7, 6, 12);
    private final List<Integer> distinctIntegersTopK = Arrays.asList(9, 7, 6, 12);
    private final List<Integer> nonDistinctIntegers = Arrays.asList(1, 2, 3, 3, 9, 9, 7, 6, 12);
    private final List<Integer> nonDistinctIntegersTopK = Arrays.asList(9, 9, 7, 12);


    @Test
    void givenArrayDistinctIntegers_whenBruteForceFindTopK_thenReturnKLargest() {
        assertThat(bruteForceFinder.findTopK(distinctIntegers, k)).containsOnlyElementsOf(distinctIntegersTopK);
    }

    @Test
    void givenArrayDistinctIntegers_whenMaxHeapFindTopK_thenReturnKLargest() {
        assertThat(maxHeapFinder.findTopK(distinctIntegers, k)).containsOnlyElementsOf(distinctIntegersTopK);
    }

    @Test
    void givenArrayDistinctIntegers_whenTreeSetFindTopK_thenReturnKLargest() {
        assertThat(treeSetFinder.findTopK(distinctIntegers, k)).containsOnlyElementsOf(distinctIntegersTopK);
    }

    @Test
    void givenArrayNonDistinctIntegers_whenBruteForceFindTopK_thenReturnKLargest() {
        assertThat(bruteForceFinder.findTopK(nonDistinctIntegers, k)).containsOnlyElementsOf(nonDistinctIntegersTopK);
    }

    @Test
    void givenArrayNonDistinctIntegers_whenMaxHeapFindTopK_thenReturnKLargest() {
        assertThat(maxHeapFinder.findTopK(nonDistinctIntegers, k)).containsOnlyElementsOf(nonDistinctIntegersTopK);
    }
}
