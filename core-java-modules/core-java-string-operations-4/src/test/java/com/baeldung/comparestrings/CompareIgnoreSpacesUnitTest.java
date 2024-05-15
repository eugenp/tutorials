package com.baeldung.comparestrings;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompareIgnoreSpacesUnitTest {

    private static String normalString = "ABCDEF";
    private static String stringWithSpaces = " AB  CD EF ";

    @Test
    public void givenTwoStrings_thenCompareWithReplaceAllMethod() {
        assertEquals(normalString.replaceAll("\\s+",""), stringWithSpaces.replaceAll("\\s+",""));
    }

    @Test
    public void givenTwoStrings_thenCompareWithApacheStringUtils() {
        assertEquals(StringUtils.deleteWhitespace(normalString), StringUtils.deleteWhitespace(stringWithSpaces));
    }

    @Test
    public void givenTwoStrings_thenCompareWithSpringStringUtils() {
        assertEquals(org.springframework.util.StringUtils.trimAllWhitespace(normalString), org.springframework.util.StringUtils.trimAllWhitespace(stringWithSpaces));
    }

}
