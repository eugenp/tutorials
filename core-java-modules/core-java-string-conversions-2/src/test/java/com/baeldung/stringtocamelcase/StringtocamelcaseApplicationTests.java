package com.baeldung.stringtocamelcase;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StringtocamelcaseApplicationTests {

	private static final String TEXT_WHITESPACE = "THIS STRING SHOULD BE IN CAMEL CASE";
	private static final String TEXT_UNDERSCORE = "THIS_STRING_SHOULD_BE_IN_CAMEL_CASE";
	private static final String TEXT_EXPECTED = "ThisStringShouldBeInCamelCase";

	@Test
	public void testNothing() {
	}

	@Test
	public void testToCamelCaseByIteration_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseByIteration(TEXT_WHITESPACE, ' '));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseByIteration(TEXT_UNDERSCORE, '_'));
	}

	@Test
	public void testToCamelCaseBySplitting_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseBySplitting(TEXT_WHITESPACE, " "));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseBySplitting(TEXT_UNDERSCORE, "_"));
	}

	@Test
	public void testToCamelCaseBySplittingUsingStreams_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseBySplittingUsingStreams(TEXT_WHITESPACE, " "));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseBySplittingUsingStreams(TEXT_UNDERSCORE, "_"));
	}

	@Test
	public void testToCamelCaseUsingApacheCommonsText_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingApacheCommonsText(TEXT_WHITESPACE, ' '));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingApacheCommonsText(TEXT_UNDERSCORE, '_'));
	}

	@Test
	public void testToCamelCaseUsingICU4J_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingICU4J(TEXT_WHITESPACE, " "));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingICU4J(TEXT_UNDERSCORE, "_"));
	}

	@Test
	public void testToCamelCaseUsingGuava_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingGuava(TEXT_WHITESPACE, " "));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingGuava(TEXT_UNDERSCORE, "_"));
	}

	@Test
	public void testToCamelCaseUsingApacheCommons_shouldReturnCamelCase() {
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingApacheCommons(TEXT_WHITESPACE, ' '));
		assertEquals(TEXT_EXPECTED, StringtocamelcaseApplication.toCamelCaseUsingApacheCommons(TEXT_UNDERSCORE, '_'));
	}

}
