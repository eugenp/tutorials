package com.baeldung.getindicesaftersorting;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class GetIndicesAfterSortingUnitTest {
    int[] array = {40, 10, 20, 30};

    @Test
    public void givenArray_whenUsingCustomComparator_thenSortedIndicesMatchExpected() {
        Integer[] indices = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(i -> array[i]));

        assertArrayEquals(new Integer[]{1, 2, 3, 0}, indices);
    }

    @Test
    public void givenArray_whenUsingStreamAPI_thenSortedIndicesMatchExpected() {
        List<Integer> indices = IntStream.range(0, array.length)
                .boxed().sorted(Comparator.comparingInt(i -> array[i])).collect(Collectors.toList());

        assertIterableEquals(Arrays.asList(1, 2, 3, 0), indices);
    }
}
