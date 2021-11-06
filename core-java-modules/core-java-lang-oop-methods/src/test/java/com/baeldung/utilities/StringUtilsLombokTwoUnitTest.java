package com.baeldung.utilities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsLombokTwoUnitTest {

    @Test
    void givenAnEmptyString_whenCallingIsEmpty_thenResultIsTrue() {
        assertThat(StringUtilsLombokTwo.isEmpty("")).isTrue();
    }

    @Test
    void givenNonEmptyString_whenCallingIsEmpty_thenResultIsFalse() {
        assertThat(StringUtilsLombokTwo.isEmpty("asd")).isFalse();
    }

    @Test
    void givenAnEmptyString_whenCallingWrap_thenResultIsAnEmptyString() {
        assertThat(StringUtilsLombokTwo.wrap("", "wrapper")).isEmpty();
    }

    @Test
    void givenNonEmptyString_whenCallingWrap_thenResultIsWrappedString() {
        assertThat(StringUtilsLombokTwo.wrap("asd", "wrapper")).isEqualTo("wrapperasdwrapper");
    }

}
