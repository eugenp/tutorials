package com.baeldung.utilities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsLombokOneUnitTest {

    @Test
    void givenAnEmptyString_whenCallingIsEmpty_thenResultIsTrue() {
        assertThat(StringUtilsLombokOne.isEmpty("")).isTrue();
    }

    @Test
    void givenNonEmptyString_whenCallingIsEmpty_thenResultIsFalse() {
        assertThat(StringUtilsLombokOne.isEmpty("asd")).isFalse();
    }

    @Test
    void givenAnEmptyString_whenCallingWrap_thenResultIsAnEmptyString() {
        assertThat(StringUtilsLombokOne.wrap("", "wrapper")).isEmpty();
    }

    @Test
    void givenNonEmptyString_whenCallingWrap_thenResultIsWrappedString() {
        assertThat(StringUtilsLombokOne.wrap("asd", "wrapper")).isEqualTo("wrapperasdwrapper");
    }

}
