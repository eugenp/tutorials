package com.baeldung.java.conversion;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class StringToCharArray {
	
	public char[] stringToCharArray(String originalString) {
	    char[] charArray = new char[originalString.length()];
	    for(int i = 0; i < originalString.length(); i++)
	    {
	         charArray[i] = originalString.charAt(i);
	    }
	    return charArray;
	}
	
	@Test
	public void whenStringConvertedToCharArray_thenCorrect() {
	    String testString = "test";
	    char[] charArray = {'t', 'e', 's', 't' };
	    assertEquals(Arrays.equals(stringToCharArray(testString), charArray), true);
	}
	
	@Test
	public void whenStringConvertedToCharArrayUsingStringMethods_thenCorrect() {
	    String testString = "test";
	    char[] charArray = {'t', 'e', 's', 't'};
	    char[] getCharsCharArray = new char[testString.length()];
	    testString.getChars(0, testString.length(), getCharsCharArray, 0);
	    assertEquals(Arrays.equals(testString.toCharArray(), charArray), true);
	    assertEquals(Arrays.equals(getCharsCharArray, charArray), true);
	}
	
	@Test
	public void whenCharArrayConvertedToString_thenCorrect() {
	    char[] charArray = {'t', 'e', 's', 't'};
	    String testString = "test";
	    assertEquals(new String(charArray), testString);
	    assertEquals(String.valueOf(charArray), testString);
	    assertEquals(String.copyValueOf(charArray), testString);
	}

}
