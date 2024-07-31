package com.baeldung.stringbuildercomparison;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

import org.junit.Test;

import stringbuildercomparison.StringBuilderCompare;

public class StringBuilderComparisonUnitTest {

    @Test
    public void whenUsingJavaEight_givenTwoIdenticalStringBuilders_thenCorrectlyMatch(){
        StringBuilder one = new StringBuilder("Hello");
        StringBuilder two = new StringBuilder("Hello");
        boolean result = StringBuilderCompare.compare(one, two);
        assertEquals(true, result);
    }

    @Test
    public void whenUsingJavaEight_givenTwoDifferentStringBuilders_thenCorrectlyIdentifyDifference(){
        StringBuilder one = new StringBuilder("Hello");
        StringBuilder two = new StringBuilder("World");
        boolean result = StringBuilderCompare.compare(one, two);
        assertEquals(false, result);
    }

    @Test
    public void whenUsingJavaEleven_givenTwoIdenticalStringBuilders_thenCorrectlyMatch(){
        StringBuilder one = new StringBuilder("Hello");
        StringBuilder two = new StringBuilder("Hello");
        assertEquals(0, one.compareTo(two));
    }

    @Test
    public void whenUsingJavaEleven_givenTwoDifferentStringBuilders_thenCorrectlyIdentifyDifference(){
        StringBuilder one = new StringBuilder("Hello");
        StringBuilder two = new StringBuilder("World");
        assertNotSame(0, one.compareTo(two));
    }


}
