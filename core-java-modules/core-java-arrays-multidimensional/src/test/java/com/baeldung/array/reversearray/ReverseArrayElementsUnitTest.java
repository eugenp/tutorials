package com.baeldung.array.reversearray;

import static com.baeldung.array.reversearray.ReverseArrayElements.toReversedList;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ReverseArrayElementsUnitTest {

    @Test
    void givenArray_whenCallReverseRows_thenAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingSimpleForLoops(input);

        assertThat(input).isEqualTo(expected);
    }

    @Test
    void givenArray_whenCallReverseRowsUsingNestedIntStreams_thenAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingNestedIntStreams(input);

        assertThat(input).isEqualTo(expected);
    }

    @Test
    void givenArray_whenCallReverseRowsUsingCollectionsReverse_thenAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingCollectionsReverse(input);

        assertThat(input).isEqualTo(expected);
    }

    @Test
    void givenArray_whenCallReverseRowsUsingCollectionsReverseWithList_thenAllRowsReversed() {
        List<List<Integer>> input = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        ReverseArrayElements.reverseRowsUsingCollectionsReverse(input);

        assertThat(input).isEqualTo(expected);
    }

    @Test
    void givenStreamOf2dArrays_whenMappingWithReverse_thenOutputStreamIsAlsoReversed() {
        List<List<Integer>> input = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        List<List<Integer>> result = input.stream()
            .map(ReverseArrayElements::reverse)
            .collect(Collectors.toList());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenStreamOf2dArrays_whenMappingWithCollectReversed_thenOutputStreamIsAlsoReversed() {
        List<List<Integer>> input = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        List<List<Integer>> result = input.stream()
            .map(a -> a.stream()
                .collect(toReversedList()))
            .collect(Collectors.toList());

        assertThat(result).isEqualTo(expected);
    }
}