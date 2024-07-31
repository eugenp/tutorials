package com.baeldung.strcontainsnumber;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

public class StrContainsNumberUtilsUnitTest {

    private static final String INPUT_WITH_NUMBERS = "We hope 2024 will be great";
    private static final String INPUT_WITHOUT_NUMBERS = "Hello world";

    @Test
    public void givenInputString_whenUsingMatchesMethod_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingMatchesMethod(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingMatchesMethod(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingMatchesMethod(""));
        assertFalse(StrContainsNumberUtils.checkUsingMatchesMethod(null));
    }

    @Test
    public void givenInputString_whenUsingPatternClass_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingPatternClass(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingPatternClass(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingPatternClass(""));
        assertFalse(StrContainsNumberUtils.checkUsingPatternClass(null));
    }

    @Test
    public void givenInputString_whenUsingReplaceAllMethod_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingReplaceAllMethod(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingReplaceAllMethod(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingReplaceAllMethod(""));
        assertFalse(StrContainsNumberUtils.checkUsingReplaceAllMethod(null));
    }

    @Test
    public void givenInputString_whenUsingIsDigitMethod_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingIsDigitMethod(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingIsDigitMethod(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingIsDigitMethod(""));
        assertFalse(StrContainsNumberUtils.checkUsingIsDigitMethod(null));
    }

    @Test
    public void givenInputString_whenUsingStreamApi_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingStreamApi(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingStreamApi(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingStreamApi(""));
        assertFalse(StrContainsNumberUtils.checkUsingStreamApi(null));
    }

    @Test
    public void givenInputString_whenUsingApacheCommonsLang_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingApacheCommonsLang(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingApacheCommonsLang(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingApacheCommonsLang(""));
        assertFalse(StrContainsNumberUtils.checkUsingApacheCommonsLang(null));
    }

    @Test
    public void givenInputString_whenUsingGuava_ThenCheck() {
        assertTrue(StrContainsNumberUtils.checkUsingGuava(INPUT_WITH_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingGuava(INPUT_WITHOUT_NUMBERS));
        assertFalse(StrContainsNumberUtils.checkUsingGuava(""));
        assertFalse(StrContainsNumberUtils.checkUsingGuava(null));
    }

}
