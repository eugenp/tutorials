package com.baeldung.string.interview;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class StringToCharArrayUnitTest {
    @Test
    public void whenConvertingStringToCharArray_thenConversionSuccessful() {
        String beforeConvStr = "hello";
        char[] afterConvCharArr = { 'h', 'e', 'l', 'l', 'o' };
      
        assertEquals(Arrays.equals(beforeConvStr.toCharArray(), afterConvCharArr), true);
    }
}
