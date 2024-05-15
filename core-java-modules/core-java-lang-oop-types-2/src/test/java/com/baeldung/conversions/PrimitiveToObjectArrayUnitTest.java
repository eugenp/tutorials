package com.baeldung.conversions;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveToObjectArrayUnitTest {

    @Test
    void givenUsingIteration_whenConvertingToObjects_thenSuccess() {
        int[] input = new int[] { 0, 1, 2, 3, 4 };
        Integer[] expected = new Integer[] { 0, 1, 2, 3, 4 };

        Integer[] output = new Integer[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }

        assertArrayEquals(expected, output);
    }

    @Test
    void givenUsingIteration_whenConvertingToPrimitives_thenSuccess() {
        Integer[] input = new Integer[] { 0, 1, 2, 3, 4 };
        int[] expected = new int[] { 0, 1, 2, 3, 4 };

        int[] output = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }

        assertArrayEquals(expected, output);
    }

    @Test
    void givenUsingStreams_whenConvertingToObjects_thenSuccess() {
        int[] input = new int[] { 0, 1, 2, 3, 4 };
        Integer[] expected = new Integer[] { 0, 1, 2, 3, 4 };

        Integer[] output = Arrays.stream(input)
            .boxed()
            .toArray(Integer[]::new);

        assertArrayEquals(expected, output);
    }

    @Test
    void givenUsingStreams_whenConvertingToPrimitives_thenSuccess() {
        Integer[] input = new Integer[] { 0, 1, 2, 3, 4 };
        int[] expected = new int[] { 0, 1, 2, 3, 4 };

        int[] output = Arrays.stream(input)
            .mapToInt(Integer::intValue)
            .toArray();

        assertArrayEquals(expected, output);
    }

    @Test
    void givenUsingApacheCommons_whenConvertingToObjects_thenSuccess() {
        int[] input = new int[] { 0, 1, 2, 3, 4 };
        Integer[] expected = new Integer[] { 0, 1, 2, 3, 4 };

        Integer[] output = ArrayUtils.toObject(input);

        assertArrayEquals(expected, output);
    }

    @Test
    void givenUsingApacheCommons_whenConvertingToPrimitives_thenSuccess() {
        Integer[] input = new Integer[] { 0, 1, 2, 3, 4 };
        int[] expected = new int[] { 0, 1, 2, 3, 4 };

        int[] output = ArrayUtils.toPrimitive(input);

        assertArrayEquals(expected, output);
    }
}