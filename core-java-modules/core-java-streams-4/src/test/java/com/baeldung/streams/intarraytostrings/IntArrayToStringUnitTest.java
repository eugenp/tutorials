package com.baeldung.streams.intarraytostrings;

import static com.baeldung.streams.intarraytostrings.ArrayConversionUtils.convertToString;
import static com.baeldung.streams.intarraytostrings.ArrayConversionUtils.convertToStringArray;
import static com.baeldung.streams.intarraytostrings.ArrayConversionUtils.convertToStringArrayWithBoxing;

import org.junit.Assert;
import org.junit.Test;

public class IntArrayToStringUnitTest {

    @Test
    public void whenConvertingIntegers_thenHandleStreamOfIntegers() {
        Integer[] integerNumbers = { 1, 2, 3, 4, 5 };
        String[] expectedOutput = { "1", "2", "3", "4", "5" };

        String[] strings = convertToStringArray(integerNumbers);

        Assert.assertArrayEquals(expectedOutput, strings);
    }

    @Test
    public void whenConvertingInts_thenHandleIntStream() {
        int[] intNumbers = { 1, 2, 3, 4, 5 };
        String[] expectedOutput = { "1", "2", "3", "4", "5" };

        String[] strings = convertToStringArray(intNumbers);

        Assert.assertArrayEquals(expectedOutput, strings);
    }

    @Test
    public void givenAnIntArray_whenBoxingToInteger_thenHandleStreamOfIntegers() {
        int[] intNumbers = { 1, 2, 3, 4, 5 };
        String[] expectedOutput = { "1", "2", "3", "4", "5" };

        String[] strings = convertToStringArrayWithBoxing(intNumbers);

        Assert.assertArrayEquals(expectedOutput, strings);
    }

    @Test
    public void givenAnIntArray_whenUsingCollectorsJoining_thenReturnCommaSeparatedString(){
        int[] intNumbers = { 1, 2, 3, 4, 5 };
        String expectedOutput = "1, 2, 3, 4, 5";

        String string = convertToString(intNumbers);

        Assert.assertEquals(expectedOutput, string);
    }

}
