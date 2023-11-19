package com.baeldung.arrayslicing;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SlicingArrayUnitTest {
    private static final String[] LANGUAGES = new String[] { "Python", "Java", "Kotlin", "Scala", "Ruby", "Go", "Rust" };
    private static final String[] JVM_LANGUAGES = new String[] { "Java", "Kotlin", "Scala" };

    @Test
    void givenAnArray_whenSlicingUsingStream_shouldGetExpectedResult() {
        String[] result = Arrays.stream(LANGUAGES, 1, 4).toArray(String[]::new);
        assertArrayEquals(JVM_LANGUAGES, result);
    }

    @Test
    void givenAnArray_whenSlicingUsingArraysCopyOfRange_shouldGetExpectedResult() {
        String[] result = Arrays.copyOfRange(LANGUAGES, 1, 4);
        assertArrayEquals(JVM_LANGUAGES, result);
    }


    @Test
    void givenAnArray_whenSlicingUsingSystemArraycopy_shouldGetExpectedResult() {
        String[] result = new String[3];
        System.arraycopy(LANGUAGES, 1, result, 0, 3);
        assertArrayEquals(JVM_LANGUAGES, result);

        String[] result2 = new String[] { "value one", "value two", "value three", "value four", "value five", "value six", "value seven" };
        System.arraycopy(LANGUAGES, 1, result2, 2, 3);
        assertArrayEquals(new String[] { "value one", "value two", "Java", "Kotlin", "Scala", "value six", "value seven" }, result2);
    }

    @Test
    void givenAnArray_whenSlicingUsingArrayUtils_shouldGetExpectedResult() {
        String[] result = ArrayUtils.subarray(LANGUAGES, 1, 4);
        assertArrayEquals(JVM_LANGUAGES, result);
    }
}
