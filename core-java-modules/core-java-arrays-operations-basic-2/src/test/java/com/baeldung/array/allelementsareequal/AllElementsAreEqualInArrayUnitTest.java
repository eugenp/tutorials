package com.baeldung.array.allelementsareequal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class AllElementsAreEqualInArrayUnitTest {

    // all equal = true arrays:
    private final static String[] ARRAY_ALL_EQ = { "java", "java", "java", "java" };
    private final static String[] ARRAY_ALL_NULL = { null, null, null, null };
    private final static String[] ARRAY_SINGLE_EL = { "java" };

    // all equal = false arrays:
    private final static String[] ARRAY_NOT_EQ = { "java", "kotlin", "java", "java" };
    private final static String[] ARRAY_EMPTY = {};
    private final static String[] ARRAY_NULL = null;

    //int[] arrays
    private final static int[] INT_ARRAY_ALL_EQ = { 7, 7, 7, 7 };
    private final static int[] INT_ARRAY_NOT_EQ = { 7, 7, 7, 42 };
    private final static int[] INT_ARRAY_SINGLE_EL = { 42 };

    public <T> boolean isAllEqual(T[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = 1; i < array.length; i++) {
            if (!Objects.equals(array[0], array[i])) {
                return false;
            }
        }
        return true;
    }

    @Test
    void whenUsingArraysAreEqual_thenCorrect() {
        assertTrue(isAllEqual(ARRAY_ALL_EQ));
        assertTrue(isAllEqual(ARRAY_ALL_NULL));
        assertTrue(isAllEqual(ARRAY_SINGLE_EL));

        assertFalse(isAllEqual(ARRAY_NOT_EQ));
        assertFalse(isAllEqual(ARRAY_EMPTY));
        assertFalse(isAllEqual(ARRAY_NULL));
    }

    public <T> boolean isAllEqualByDistinct(T[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return Arrays.stream(array)
            .distinct()
            .count() == 1;
    }

    @Test
    void whenUsingArraysAreEqualByDistinct_thenCorrect() {
        assertTrue(isAllEqualByDistinct(ARRAY_ALL_EQ));
        assertTrue(isAllEqualByDistinct(ARRAY_ALL_NULL));
        assertTrue(isAllEqualByDistinct(ARRAY_SINGLE_EL));

        assertFalse(isAllEqualByDistinct(ARRAY_NOT_EQ));
        assertFalse(isAllEqualByDistinct(ARRAY_EMPTY));
        assertFalse(isAllEqualByDistinct(ARRAY_NULL));
    }

    public <T> boolean isAllEqualByAllMatch(T[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return Arrays.stream(array)
            .allMatch(element -> Objects.equals(array[0], element));
    }

    @Test
    void whenUsingArraysAreEqualByAllMatch_thenCorrect() {
        assertTrue(isAllEqualByAllMatch(ARRAY_ALL_EQ));
        assertTrue(isAllEqualByAllMatch(ARRAY_ALL_NULL));
        assertTrue(isAllEqualByAllMatch(ARRAY_SINGLE_EL));

        assertFalse(isAllEqualByAllMatch(ARRAY_NOT_EQ));
        assertFalse(isAllEqualByAllMatch(ARRAY_EMPTY));
        assertFalse(isAllEqualByAllMatch(ARRAY_NULL));
    }

    // primitive array approaches below (int[] as example)

    public boolean isAllEqual(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[0] != array[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    void whenUsingArraysAreEqualForIntArrays_thenCorrect() {
        assertTrue(isAllEqual(INT_ARRAY_ALL_EQ));
        assertTrue(isAllEqual(INT_ARRAY_SINGLE_EL));

        assertFalse(isAllEqual(INT_ARRAY_NOT_EQ));
    }

    public boolean isAllEqualByDistinct(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return IntStream.of(array)
            .distinct()
            .count() == 1;
    }

    @Test
    void whenUsingArraysAreEqualByDistinctForIntArrays_thenCorrect() {
        assertTrue(isAllEqualByDistinct(INT_ARRAY_ALL_EQ));
        assertTrue(isAllEqualByDistinct(INT_ARRAY_SINGLE_EL));

        assertFalse(isAllEqualByDistinct(INT_ARRAY_NOT_EQ));
    }

    public boolean isAllEqualByAllMatch(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return IntStream.of(array)
            .allMatch(element -> array[0] == element);
    }

    @Test
    void whenUsingArraysAreEqualByAllMatchForIntArrays_thenCorrect() {
        assertTrue(isAllEqualByAllMatch(INT_ARRAY_ALL_EQ));
        assertTrue(isAllEqualByAllMatch(INT_ARRAY_SINGLE_EL));

        assertFalse(isAllEqualByAllMatch(INT_ARRAY_NOT_EQ));
    }
}