package com.baeldung.removebrackets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class RemoveBracketsFromStringUnitTest {

    private static final String INPUT = "This (is) <a> [nice] {string}!";
    private static final String INPUT_WITH_UNICODE = "⟨T⟩❰h❱「i」⦇s⦈ (is) <a> [nice] {string}!";

    @Test
    void whenUsingStringUtils_thenCorrect() {
        String result = StringUtils.replaceChars(INPUT, "(){}[]<>", null);
        assertEquals("This is a nice string!", result);
    }

    @Test
    void whenUsingReplaceAll_thenCorrect() {
        String regex = "[(){}<>\\[\\]]";
        String result = INPUT.replaceAll(regex, "");
        assertEquals("This is a nice string!", result);
    }

    @Test
    void whenUsingUnicodeProperty_thenLessThanAndGreaterThanAreNotCovered() {
        String regex = "\\p{Ps}|\\p{Pe}";

        String result = INPUT.replaceAll(regex, "");
        assertEquals("This is <a> nice string!", result);

        String resultWithUnicode = INPUT_WITH_UNICODE.replaceAll(regex, "");
        assertEquals("This is <a> nice string!", resultWithUnicode);
    }

    @Test
    void whenUsingUnicodePropertyWithCharClass_thenLessThanAndGreaterThanAreCovered() {
        String regex = "\\p{Ps}|\\p{Pe}|[<>]";

        String result = INPUT.replaceAll(regex, "");
        assertEquals("This is a nice string!", result);

        String resultWithUnicode = INPUT_WITH_UNICODE.replaceAll(regex, "");
        assertEquals("This is a nice string!", resultWithUnicode);
    }

}