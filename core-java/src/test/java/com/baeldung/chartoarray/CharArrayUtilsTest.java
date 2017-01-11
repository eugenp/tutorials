package com.baeldung.chartoarray;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CharArrayUtilsTest {

    @Test
    public void givenString_whenCallingStringToCharArray_shouldConvertToCharArray() {
        String givenString = "abcd";

        char[] result = CharArrayUtils.stringToCharArray(givenString);

        assertTrue(Arrays.equals(result, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenCharArray_whenCallingCharArrayToString_shouldConvertToString() {
        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        assertThat(CharArrayUtils.charArrayToString(givenCharArray)).isEqualTo("abcd");
    }

}
