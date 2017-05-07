/**
 * 
 */
package com.baeldung.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author iaforek
 *
 */
public class RemoveLastCharTest {
	
	public static final String TEST_STRING = "abcdef";
	public static final String NULL_STRING = null;
	public static final String EMPTY_STRING = "";
	public static final String ONE_CHAR_STRING = "a";
	public static final String WHITE_SPACE_AT_THE_END_STRING = "abc ";

	/**
	 * Test method for {@link com.baeldung.string.RemoveLastChar#substring(java.lang.String)}.
	 */
	@Test
	public void givenTestString_substring_getStingWithoutLastChar() {
		Assert.assertEquals("abcde", RemoveLastChar.substring(TEST_STRING));
		Assert.assertEquals("abcde", StringUtils.chop(TEST_STRING));
	}
	
	@Test
	public void givenNullString_substring_getNullString() {
		Assert.assertEquals(NULL_STRING, RemoveLastChar.substring(NULL_STRING));
		Assert.assertEquals(NULL_STRING, StringUtils.chop(NULL_STRING));

	}

	@Test
	public void givenEmptyString_substring_getEmptyString() {
		Assert.assertEquals(NULL_STRING, RemoveLastChar.substring(EMPTY_STRING));
		Assert.assertEquals(NULL_STRING, StringUtils.chop(NULL_STRING));
	}
	
	@Test
	public void givenOneCharString_substring_getEmptyString() {
		Assert.assertEquals(EMPTY_STRING, RemoveLastChar.substring(ONE_CHAR_STRING));
		Assert.assertEquals(EMPTY_STRING, StringUtils.chop(ONE_CHAR_STRING));
	}
	
	@Test
	public void givenStringWithWhiteSpaceAtTheEnd_substring_getStringWithoutWhiteSpaceAtTheEnd() {
		Assert.assertEquals("abc", RemoveLastChar.substring(WHITE_SPACE_AT_THE_END_STRING));
		Assert.assertEquals("abc", StringUtils.chop(WHITE_SPACE_AT_THE_END_STRING));
	}
	
}
