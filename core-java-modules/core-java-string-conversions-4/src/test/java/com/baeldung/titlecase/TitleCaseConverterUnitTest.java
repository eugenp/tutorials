package com.baeldung.titlecase;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TitleCaseConverterUnitTest {

    private static final String TEXT = "tHis IS a tiTLe";
    private static final String TEXT_EXPECTED = "This Is A Title";
    private static final String TEXT_EXPECTED_NOT_FULL = "THis IS A TiTLe";

    private static final String TEXT_OTHER_DELIMITERS = "tHis, IS a   tiTLe";
    private static final String TEXT_EXPECTED_OTHER_DELIMITERS = "This, Is A   Title";
    private static final String TEXT_EXPECTED_OTHER_DELIMITERS_NOT_FULL = "THis, IS A   TiTLe";

    @Test
    public void whenConvertingToTitleCaseIterating_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, TitleCaseConverter.convertToTitleCaseIteratingChars(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseSplitting_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, TitleCaseConverter.convertToTitleCaseSplitting(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingWordUtilsFull_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, TitleCaseConverter.convertToTileCaseWordUtilsFull(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingWordUtils_thenStringConvertedOnlyFirstCharacter() {
        assertEquals(TEXT_EXPECTED_NOT_FULL, TitleCaseConverter.convertToTileCaseWordUtils(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingIcu4j_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, TitleCaseConverter.convertToTitleCaseIcu4j(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseWithDifferentDelimiters_thenDelimitersKept() {
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, TitleCaseConverter.convertToTitleCaseIteratingChars(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, TitleCaseConverter.convertToTitleCaseSplitting(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, TitleCaseConverter.convertToTileCaseWordUtilsFull(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS_NOT_FULL, TitleCaseConverter.convertToTileCaseWordUtils(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, TitleCaseConverter.convertToTitleCaseIcu4j(TEXT_OTHER_DELIMITERS));
    }

    @Test
    public void givenNull_whenConvertingToTileCase_thenReturnNull() {
        assertEquals(null, TitleCaseConverter.convertToTitleCaseIteratingChars(null));
        assertEquals(null, TitleCaseConverter.convertToTitleCaseSplitting(null));
        assertEquals(null, TitleCaseConverter.convertToTileCaseWordUtilsFull(null));
        assertEquals(null, TitleCaseConverter.convertToTileCaseWordUtils(null));
        assertEquals(null, TitleCaseConverter.convertToTitleCaseIcu4j(null));
    }

    @Test
    public void givenEmptyString_whenConvertingToTileCase_thenReturnEmptyString() {
        assertEquals("", TitleCaseConverter.convertToTitleCaseIteratingChars(""));
        assertEquals("", TitleCaseConverter.convertToTitleCaseSplitting(""));
        assertEquals("", TitleCaseConverter.convertToTileCaseWordUtilsFull(""));
        assertEquals("", TitleCaseConverter.convertToTileCaseWordUtils(""));
        assertEquals("", TitleCaseConverter.convertToTitleCaseIcu4j(""));
    }

}
