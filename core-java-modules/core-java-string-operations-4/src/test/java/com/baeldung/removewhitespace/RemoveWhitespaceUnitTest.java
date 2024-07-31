package com.baeldung.removewhitespace;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveWhitespaceUnitTest {
    private final String myString = "   I    am a    wonderful String     !   ";

    @Test
    void givenStringWithWhitespace_whenRemoveAllWhitespace_shouldGetExpectedResult() {
        String result = myString.replaceAll("\\s", "");
        assertThat(result).isEqualTo("IamawonderfulString!");
    }

    @Test
    void givenStringWithWhitespace_whenRemoveAllWhitespaceUsingStringUtils_shouldGetExpectedResult() {
        String result = StringUtils.deleteWhitespace(myString);
        assertThat(result).isEqualTo("IamawonderfulString!");
    }

    @Test
    void givenStringWithWhitespace_whenReplaceConsecutiveSpacesWithSingleSpace_shouldGetExpectedResult() {
        String result = myString.replaceAll("\\s+", " ");
        assertThat(result).isEqualTo(" I am a wonderful String ! ");
        assertThat(result.trim()).isEqualTo("I am a wonderful String !");
    }

    @Test
    void givenStringWithWhitespace_whenNormalizeWithApacheCommons_shouldGetExpectedResult() {
        String result = StringUtils.normalizeSpace(myString);
        assertThat(result).isEqualTo("I am a wonderful String !");
    }
}
