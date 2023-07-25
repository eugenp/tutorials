package com.baeldung.nonalphanumeric;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NonAlphaNumericCheckerUnitTest {
    @Test
    public void whenStrLatinOrHasNonAlphaNum_ThenRetTrue() {
        String latin = Character.UnicodeScript.LATIN.name();
        //alphabets with special character
        String str1 = "W$nder^ful";
        //digits with special character
        String str2 = "123$%45";
        //alphabets and digits with special characters
        String str3 = "W@nd$r123$%45";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertTrue(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str1, latin));
        assertTrue(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str2, latin));
        assertTrue(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str3, latin));
    }

    @Test
    public void whenStrLatinOrHasNoNonAlphaNum_ThenRetFalse() {
        String latin = Character.UnicodeScript.LATIN.name();
        //only alphabets
        String str1 = "Wonderful";
        //only digits
        String str2 = "12345";
        //mix of alphabet and digit
        String str3 = "5Won6der1234";
        //Error message
        String ERROR_MSG = "Test failed, non alphanumeric char found in ";

        assertFalse(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str1, latin));
        assertFalse(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str2, latin));
        assertFalse(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str3, latin));
    }

    @Test
    public void whenStrNonLatinOrHasNonAlphaNum_ThenRetTrue() {
        String latin = Character.UnicodeScript.LATIN.name();
        //special character in Georgian text
        String str1 = "##მშვენიერი@";
        //special character with Turkish  text
        String str2 = "müthiş#$";
        //No special character in Georgian text
        String str3 = "მშვენიერი";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertTrue(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str1, latin));
        assertTrue(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str2, latin));
        assertTrue(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str3, latin));
    }

    @Test
    public void whenStrAnyLangOrHasNonAlphaNum_ThenRetTrue() {
        //special character in Georgian text
        String str1 = "##მშვენიერი@";
        //special character with Turkish  text
        String str2 = "müthiş#$";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertTrue(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericAnyLangScript(str1));
        assertTrue(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericAnyLangScript(str2));
    }

    @Test
    public void whenStrAnyLangOrHasNoNonAlphaNum_ThenRetFalse() {
        //Georgian text with no special char
        String str1 = "მშვენიერი";
        //Turkish  text with no special char
        String str2 = "müthiş";
        //Latin text with no special char
        String str3 = "Wonderful";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertFalse(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericAnyLangScript(str1));
        assertFalse(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericAnyLangScript(str2));
        assertFalse(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericAnyLangScript(str3));
    }

    @Test
    public void givenLang_whenStrHasDiffLangOrHasNonAlphaNum_ThenRetTrue() {
        String script1 = Character.UnicodeScript.MYANMAR.name(); //script used for Burmese Lang
        //text in Burmese with special char
        String str1 = "အံ့ဩ%စ##ရာ";
        //special character and english char in Burmese
        String str2 = "အံ့ADFဩစ%ရာ*^";
        //English character in Burmese
        String str3 = "အံ့ဩစTရာWon";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertTrue(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str1, script1));
        assertTrue(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str2, script1));
        assertTrue(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str3, script1));
    }

    @Test
    public void givenLang_whenStrHasSameLangOrHasNoNonAlphaNum_ThenRetFalse() {
        String script1 = Character.UnicodeScript.MYANMAR.name(); //script used for Burmese Lang
        String script2 = Character.UnicodeScript.GREEK.name();
        //text in Burmese
        String str1 = "အံ့ဩစရာ";
        //text in Greek
        String str2 = "Εκπληκτικός";
        //Error message
        String ERROR_MSG = "Test failed, alphanumeric char found in ";

        assertFalse(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str1, script1));
        assertFalse(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericInLangScript(str2, script2));
    }

    @Test
    public void givenComLangImpl_whenStrAnyLangOrHasNonAlphaNum_ThenRetTrue() {
        //special character in Georgian text
        String str1 = "##მშვენიერი@";
        //special character with Turkish  text
        String str2 = "müthiş#$";
        //Error message
        String ERROR_MSG = "Test failed, no alphanumeric char found in ";

        assertTrue(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str1));
        assertTrue(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str2));
    }

    @Test
    public void givenComLangImpl_whenStrAnyLangOrHasNoNonAlphaNum_ThenRetFalse() {
        //Georgian text with no special char
        String str1 = "მშვენიერი";
        //Turkish  text with no special char
        String str2 = "müthiş";
        //Latin text with no special char
        String str3 = "Wonderful";
        //Burmese in Myanmar script
//        String str4 = "အံ့ဩစရာ";
        //only digits
        String str5 = "3465";
        //Error message
        String ERROR_MSG = "Test failed, alphanumeric char found in ";

        assertFalse(ERROR_MSG + str1, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str1));
        assertFalse(ERROR_MSG + str2, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str2));
        assertFalse(ERROR_MSG + str3, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str3));
        //StringUtils.isAlphanumeric is unable to support supplementary unicode character and hence fails
        //assertFalse(ERROR_MSG + str4, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str4));
        assertFalse(ERROR_MSG + str5, NonAlphaNumericChecker.isNonAlphanumericAnyLangScriptV2(str5));
    }
}



