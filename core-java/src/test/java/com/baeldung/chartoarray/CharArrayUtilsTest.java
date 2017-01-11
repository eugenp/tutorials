package com.baeldung.chartoarray;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CharArrayUtilsTest {

    @Test
    public void givenString_whenCallingStringToCharArray1_shouldConvertToCharArray() {
        String givenString = "abcd";

        char[] result = CharArrayUtils.stringToCharArray1(givenString);

        assertTrue(Arrays.equals(result, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenString_whenCallingStringToCharArray2_shouldConvertToCharArray() {
        String givenString = "abcd";

        char[] result = CharArrayUtils.stringToCharArray2(givenString);

        assertTrue(Arrays.equals(result, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenCharArray_whenCallingCharArrayToString1_shouldConvertToString() {
        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        assertThat(CharArrayUtils.charArrayToString1(givenCharArray)).isEqualTo("abcd");
    }

    @Test
    public void givenCharArray_whenCallingCharArrayToString2_shouldConvertToString() {
        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        assertThat(CharArrayUtils.charArrayToString2(givenCharArray)).isEqualTo("abcd");
    }

}
