package com.baeldung.array.conversions;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class StringArrayToIntArrayUnitTest {
    private final String[] stringArray = new String[] { "1", "2", "3", "4", "5", "6", "42" };
    private final int[] expected = new int[] { 1, 2, 3, 4, 5, 6, 42 };

    private final String[] stringArrayWithInvalidNum = new String[] { "1", "2", "hello", "4", "world", "6", "42" };
    private final int[] expectedWithInvalidInput = new int[] { 1, 2, Integer.MIN_VALUE, 4, Integer.MIN_VALUE, 6, 42 };

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void givenStringArray_whenUseStreamApi_shouldGetExpectedIntArray() {
        int[] result = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();
        assertArrayEquals(expected, result);
    }

    @Test
    void givenStringArrayWithInvalidNum_whenUseStreamApi_shouldGetExpectedIntArray() {
        int[] result = Arrays.stream(stringArrayWithInvalidNum).mapToInt(s -> {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                logger.warn("Invalid number format detected: {}, use Int.MinValue as the fallback", s);
                return Integer.MIN_VALUE;
            }
        }).toArray();
        assertArrayEquals(expectedWithInvalidInput, result);
    }

    @Test
    void givenStringArray_whenConvertInLoop_shouldGetExpectedIntArray() {
        int[] result = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            result[i] = Integer.parseInt(stringArray[i]);
        }
        assertArrayEquals(expected, result);
    }

    @Test
    void givenStringArrayWithInvalidNum_whenConvertInLoop_shouldGetExpectedIntArray() {
        int[] result = new int[stringArrayWithInvalidNum.length];
        for (int i = 0; i < stringArrayWithInvalidNum.length; i++) {
            try {
                result[i] = Integer.parseInt(stringArrayWithInvalidNum[i]);
            } catch (NumberFormatException exception) {
                logger.warn("Invalid number format detected: [{}], use Int.MinValue as the fallback", stringArrayWithInvalidNum[i]);
                result[i] = Integer.MIN_VALUE;
            }
        }

        assertArrayEquals(expectedWithInvalidInput, result);
    }
}
