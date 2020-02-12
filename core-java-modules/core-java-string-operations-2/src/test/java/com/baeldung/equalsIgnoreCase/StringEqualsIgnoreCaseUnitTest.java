package com.baeldung.equalsIgnoreCase;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringEqualsIgnoreCaseUnitTest {
    private String string1 = "equals ignore case";
    private String string2 = "EQUALS IGNORE CASE";

    @Test
    public void givenEqualStringsWithDifferentCase_whenUsingEqualsIgnoreCase_ThenTheyAreEqual() {
        assertThat(string1.equalsIgnoreCase(string2)).isTrue();
    }

    @Test
    public void givenEqualStringsWithDifferentCase_whenUsingApacheCommonsEqualsIgnoreCase_ThenTheyAreEqual() {
        assertThat(StringUtils.equalsIgnoreCase(string1, string2)).isTrue();
    }

    @Test
    public void givenAStringAndNullValue_whenUsingApacheCommonsEqualsIgnoreCase_ThenTheyAreNotEqual() {
        assertThat(StringUtils.equalsIgnoreCase(string1, null)).isFalse();
    }
}
