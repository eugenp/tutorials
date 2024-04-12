package com.baeldung.interview;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StringToCharArrayUnitTest {
    @Test
    public void whenConvertingStringToCharArray_thenConversionSuccessful() {
        String beforeConvStr = "hello";
        char[] afterConvCharArr = { 'h', 'e', 'l', 'l', 'o' };
      
        assertEquals(Arrays.equals(beforeConvStr.toCharArray(), afterConvCharArr), true);
    }
}
