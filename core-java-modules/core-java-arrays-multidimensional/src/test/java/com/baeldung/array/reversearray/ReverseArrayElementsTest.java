package com.baeldung.array.reversearray;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class ReverseArrayElementsTest {

    @Test
    void givenArray_whenCallReverseRows_thenExpectAllRowsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 3, 2, 1 }, { 1, 2, 3 }, { 3, 1, 2 } };
        ReverseArrayElements.reverseRows(input);

        assertThat(input, is(expected));
    }

    @Test
    void givenArray_whenCallReverseColumns_thenExpectAllColumnsReversed() {
        int[][] input = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 2, 1, 3 } };

        int[][] expected = new int[][] { { 2, 1, 3 }, { 3, 2, 1 }, { 1, 2, 3 } };
        ReverseArrayElements.reverseColumns(input);

        assertThat(input, is(expected));
    }
}