package com.baeldung.endswithpattern;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class StringEndsWithPatternUnitTest {

    private static final String TEXT = "Welcome to baeldung.com";
    private static final String SUFFIX = "com";

    @Test
    public void givenStringAndSuffix_whenUsingStringEndsWith_thenCheck() {

        assertTrue(StringEndsWithPattern.usingStringEndsWithMethod(TEXT, SUFFIX));
    }

    @Test
    public void givenStringAndSuffix_whenUsingStringMatches_thenCheck() {

        assertTrue(StringEndsWithPattern.usingStringMatchesMethod(TEXT, SUFFIX));
    }

    @Test
    public void givenStringAndSuffix_whenUsingStringRegionMatches_thenCheck() {

        assertTrue(StringEndsWithPattern.usingStringRegionMatchesMethod(TEXT, SUFFIX));
    }

    @Test
    public void givenStringAndSuffix_whenUsingPatternClass_thenCheck() {

        assertTrue(StringEndsWithPattern.usingPatternClass(TEXT, SUFFIX));
    }

    @Test
    public void givenStringAndSuffix_whenUsingApacheCommonsLang_thenCheck() {
        
        assertTrue(StringEndsWithPattern.usingApacheCommonsLang(TEXT, SUFFIX));
    }

}
