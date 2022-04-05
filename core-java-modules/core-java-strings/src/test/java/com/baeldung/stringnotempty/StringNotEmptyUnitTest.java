package com.baeldung.stringnotempty;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.*;

public class StringNotEmptyUnitTest {

    private String text = "baeldung";

    @Test
    public void givenAString_whenCheckedForEmptyUsingJunit_shouldAssertSuccessfully() {
        assertTrue(!text.isEmpty());
        assertFalse(text.isEmpty());
        assertNotEquals("", text);
        assertNotSame("", text);
    }

    @Test
    public void givenAString_whenCheckedForEmptyUsingHamcrest_shouldAssertSuccessfully() {
        assertThat(text, not(isEmptyString()));
        assertThat(text, not(isEmptyOrNullString()));
    }

    @Test
    public void givenAString_whenCheckedForEmptyUsingCommonsLang_shouldAssertSuccessfully() {
        assertTrue(StringUtils.isNotBlank(text));
    }

    @Test
    public void givenAString_whenCheckedForEmptyUsingAssertJ_shouldAssertSuccessfully() {
        Assertions.assertThat(text).isNotEmpty();
    }

    @Test
    public void givenAString_whenCheckedForEmptyUsingGuava_shouldAssertSuccessfully() {
        assertFalse(Strings.isNullOrEmpty(text));
    }

}
