package com.baeldung.utilities.alternatives;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsInterfaceUnitTest {

    @Test
    void givenAnEmptyString_whenCallingIsEmpty_thenResultIsTrue() {
        assertThat(StringUtilsInterface.isEmpty("")).isTrue();
    }

    @Test
    void givenNonEmptyString_whenCallingIsEmpty_thenResultIsFalse() {
        assertThat(StringUtilsInterface.isEmpty("asd")).isFalse();
    }

    @Test
    void givenAnEmptyString_whenCallingWrap_thenResultIsAnEmptyString() {
        assertThat(StringUtilsInterface.wrap("", "wrapper")).isEmpty();
    }

    @Test
    void givenNonEmptyString_whenCallingWrap_thenResultIsWrappedString() {
        assertThat(StringUtilsInterface.wrap("asd", "wrapper")).isEqualTo("wrapperasdwrapper");
    }

}
