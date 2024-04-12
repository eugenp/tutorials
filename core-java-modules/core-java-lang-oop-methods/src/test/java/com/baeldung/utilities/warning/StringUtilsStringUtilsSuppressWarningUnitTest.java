package com.baeldung.utilities.warning;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsStringUtilsSuppressWarningUnitTest {

    @Test
    void givenAnEmptyString_whenCallingIsEmpty_thenResultIsTrue() {
        assertThat(StringUtilsSuppressWarning.isEmpty("")).isTrue();
    }

    @Test
    void givenNonEmptyString_whenCallingIsEmpty_thenResultIsFalse() {
        assertThat(StringUtilsSuppressWarning.isEmpty("asd")).isFalse();
    }

    @Test
    void givenAnEmptyString_whenCallingWrap_thenResultIsAnEmptyString() {
        assertThat(StringUtilsSuppressWarning.wrap("", "wrapper")).isEmpty();
    }

    @Test
    void givenNonEmptyString_whenCallingWrap_thenResultIsWrappedString() {
        assertThat(StringUtilsSuppressWarning.wrap("asd", "wrapper")).isEqualTo("wrapperasdwrapper");
    }

}
