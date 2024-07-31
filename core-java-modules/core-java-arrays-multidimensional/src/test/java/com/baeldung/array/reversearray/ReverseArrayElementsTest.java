package com.baeldung.array.reversearray;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void givenArray_whenCallReverseRowsUsingStreamsAndExtraDeque_thenExpectAllRowsReversed() {
        Stream<List<List<Integer>>> input = Stream.of(asList(asList(1, 2, 3), asList(3, 2, 1), asList(2, 1, 3)));

        input.map(ReverseArrayElements::reverse).collect(Collectors.toList());

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };

        assertThat(input, is(expected));
    }
}