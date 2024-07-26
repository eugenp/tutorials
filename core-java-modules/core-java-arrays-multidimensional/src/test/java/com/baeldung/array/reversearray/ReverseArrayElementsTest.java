package com.baeldung.array.reversearray;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    void givenArray_whenCallReverseRowsUsingStreamsAndExtraDeque_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRowsUsingStreamsAndExtraDeque(input);

        assertThat(input, is(expected));
    }
}