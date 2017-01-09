package com.baeldung.java.conversion;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StringToCharArrayTest {

    @Test
    public void whenStringConvertedToArray_thenCorrect() {
        String example3 = "Hello World";
        char[] stringArray=example3.toCharArray();

        char[] afterStringArray = new char[] {'H', 'e', 'l', 'l','o', ' ', 'W', 'o', 'r', 'l', 'd'};

        assertEquals(Arrays.equals(stringArray,afterStringArray), true);
    }

    @Test
    public void whenCharArrayConvertedToStringConstructor_thenCorrect() {
        String result = "Hello World";
        char[] stringArray = new char[] {'H', 'e', 'l', 'l','o', ' ', 'W', 'o', 'r', 'l', 'd'};

        String example1 = new String(stringArray);  //Using String Constructor
        System.out.println("example1 : " + example1);

        assertEquals(example1, result);
    }

    @Test
    public void whenCharArrayConvertedToStringValueOf_thenCorrect() {
        String result = "Hello World";
        char[] stringArray = new char[] {'H', 'e', 'l', 'l','o', ' ', 'W', 'o', 'r', 'l', 'd'};

        String example2 = String.valueOf(stringArray);//Using valueOf

        assertEquals(example2, result);
    }
}
