package com.baeldung.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void givenString_whenCallingStringToCharArray_shouldConvertToCharArray() {
        String givenString = "abcd";

        char[] result = Utils.stringToCharArray(givenString);

        assertTrue(Arrays.equals(result, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenCharArray_whenCallingCharArrayToString_shouldConvertToString() {

        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        assertThat(Utils.charArrayToString(givenCharArray)).isEqualTo("abcd");
    }

}
