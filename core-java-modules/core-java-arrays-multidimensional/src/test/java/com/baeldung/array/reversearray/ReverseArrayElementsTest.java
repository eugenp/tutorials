package com.baeldung.array.reversearray;

import static com.baeldung.array.reversearray.ReverseArrayElements.toReversedList;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ReverseArrayElementsTest {

    @Test
    void givenArray_whenCallReverseRows_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingSimpleForLoops(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseRowsUsingNestedIntStreams_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingNestedIntStreams(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseRowsUsingCollectionsReverse_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingCollectionsReverse(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseRowsUsingCollectionsReverseWithList_thenExpectAllRowsReversed() {
        List<List<Integer>> input = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        ReverseArrayElements.reverseRowsUsingCollectionsReverse(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseRowsUsingArrayUtilsReverse_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingArrayUtilsReverse(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseRowsUsingArrayUtilsReverseAndStreams_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingArrayUtilsReverseAndStream(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenStreamOf2dArrays_whenMappingWithReverse_thenOutputStreamIsAlsoReversed() {
        List<List<Integer>> array = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        List<List<Integer>> result = array.stream().map(ReverseArrayElements::reverse)
            .collect(Collectors.toList());

        assertThat(result, is(expected));
    }

    @Test
    void givenStreamOf2dArrays_whenMappingWithCollectReversed_thenOutputStreamIsAlsoReversed() {
        List<List<Integer>> array = asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3));
        List<List<Integer>> expected = asList(asList(3, 2, 1), asList(1, 2, 3), asList(3, 1, 2));

        List<List<Integer>> result = array.stream()
            .map(a -> a.stream().collect(toReversedList()))
            .collect(Collectors.toList());

        assertThat(result, is(expected));
    }
}