package com.baeldung.algorithms.heapsort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class HeapUnitTest {

    @Test
    void givenNotEmptyHeap_whenPopCalled_thenItShouldReturnSmallestElement() {
        // given
        Heap<Integer> heap = Heap.of(3, 5, 1, 4, 2);

        // when
        int head = heap.pop();

        // then
        assertThat(head).isEqualTo(1);
    }
    
    @Test
    void givenNotEmptyIterable_whenSortCalled_thenItShouldReturnElementsInSortedList() {
        // given
        List<Integer> elements = Arrays.asList(3, 5, 1, 4, 2);
        
        // when
        List<Integer> sortedElements = Heap.sort(elements);
        
        // then
        assertThat(sortedElements).isEqualTo(Arrays.asList(1, 2, 3, 4, 5));
    }

}
