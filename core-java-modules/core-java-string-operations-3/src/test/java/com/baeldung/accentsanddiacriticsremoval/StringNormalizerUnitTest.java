package com.baeldung.accentsanddiacriticsremoval;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.Normalizer;

import org.junit.jupiter.api.Test;

class StringNormalizerUnitTest {

    @Test
    public void givenNotNormalizedString_whenIsNormalized_thenReturnFalse() {
        assertFalse(Normalizer.isNormalized("āăąēîïĩíĝġńñšŝśûůŷ", Normalizer.Form.NFKD));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenRemoveAccents_thenReturnASCIIString() {
        assertEquals("aaaeiiiiggnnsssuuy", StringNormalizer.removeAccents("āăąēîïĩíĝġńñšŝśûůŷ"));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenRemoveAccentsWithApacheCommons_thenReturnASCIIString() {
        assertEquals("aaaeiiiiggnnsssuuy", StringNormalizer.removeAccentsWithApacheCommons("āăąēîïĩíĝġńñšŝśûůŷ"));
    }

    @Test
    void givenStringWithNondecomposableUnicodeCharacters_whenRemoveAccents_thenReturnOriginalString() {
        assertEquals("łđħœ", StringNormalizer.removeAccents("łđħœ"));
    }

    @Test
    void givenStringWithNondecomposableUnicodeCharacters_whenRemoveAccentsWithApacheCommons_thenReturnModifiedString() {
        assertEquals("lđħœ", StringNormalizer.removeAccentsWithApacheCommons("łđħœ"));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenUnicodeValueOfNormalizedString_thenReturnUnicodeValue() {
        assertEquals("\\u0066 \\u0069", StringNormalizer.unicodeValueOfNormalizedString("ﬁ"));
        assertEquals("\\u0061 \\u0304", StringNormalizer.unicodeValueOfNormalizedString("ā"));
        assertEquals("\\u0069 \\u0308", StringNormalizer.unicodeValueOfNormalizedString("ï"));
        assertEquals("\\u006e \\u0301", StringNormalizer.unicodeValueOfNormalizedString("ń"));
    }

    @Test
    void givenStringWithNonDecomposableUnicodeCharacters_whenUnicodeValueOfNormalizedString_thenReturnOriginalValue() {
        assertEquals("\\u0142", StringNormalizer.unicodeValueOfNormalizedString("ł"));
        assertEquals("\\u0127", StringNormalizer.unicodeValueOfNormalizedString("ħ"));
        assertEquals("\\u0111", StringNormalizer.unicodeValueOfNormalizedString("đ"));
    }
}