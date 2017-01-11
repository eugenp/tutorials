package com.baeldung.chartoarray;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CharArrayUtilsTest {

    @Test
    public void givenString_whenUsingToCharArray_thenCorrect() {
        String givenString = "abcd";

        char[] result = givenString.toCharArray();

        assertTrue(Arrays.equals(result, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenString_whenUsingIteration_thenCorrect() {
        String givenString = "abcd";

        char[] charArray = new char[givenString.length()];

        for(int i = 0; i < givenString.length(); ++i)
            charArray[i] = givenString.charAt(i);;

        assertTrue(Arrays.equals(charArray, new char[] { 'a', 'b', 'c', 'd' }));
    }

    @Test
    public void givenCharArray_whenUsingStringBuilder_thenCorrect() {
        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        StringBuilder stringBuilder = new StringBuilder();

        for (char c : givenCharArray) {
            stringBuilder.append(c);
        }

        assertThat(stringBuilder.toString()).isEqualTo("abcd");
    }

    @Test
    public void givenCharArray_whenUsingValueOf_thenCorrect() {
        char[] givenCharArray = new char[] { 'a', 'b', 'c', 'd' };

        assertThat(String.valueOf(givenCharArray)).isEqualTo("abcd");
    }

}
