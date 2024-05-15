package com.baeldung.array.conversions;

import org.junit.jupiter.api.Test;

import static com.baeldung.array.conversions.IntArrayToIterableConversionUtils.*;
import static org.junit.Assert.assertTrue;


class IntArrayToIterableConversionUtilsUnitTest {

    private int[] ints = new int[]{1, 2, 3, 4, 5};

    @Test
    void whenConvertWithStreamToList_thenGetIterable() {
        Iterable<Integer> integers = convertWithStreamToList(ints);
        assertTrue("should be Iterable", integers instanceof Iterable);
    }

    @Test
    void whenConvertWithStreamIterator_thenGetIterable() {
        Iterable<Integer> integers = convertWithStreamIterator(ints);
        assertTrue("should be Iterable", integers instanceof Iterable);
    }

    @Test
    void whenConvertWithApacheCommonsAndJavaAsList_thenGetIterable() {
        Iterable<Integer> integers = convertWithApacheCommonsAndJavaAsList(ints);
        assertTrue("should be Iterable", integers instanceof Iterable);
    }

    @Test
    void whenConvertWithGuava_thenGetIterable() {
        Iterable<Integer> integers = convertWithGuava(ints);
        assertTrue("should be Iterable", integers instanceof Iterable);
    }
}