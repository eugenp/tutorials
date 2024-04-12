package com.baeldung.utilities.alternatives;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsEnumUnitTest {

    @Test
    void givenAnEmptyString_whenCallingIsEmpty_thenResultIsTrue() {
        assertThat(StringUtilsEnum.isEmpty("")).isTrue();
    }

    @Test
    void givenNonEmptyString_whenCallingIsEmpty_thenResultIsFalse() {
        assertThat(StringUtilsEnum.isEmpty("asd")).isFalse();
    }

    @Test
    void givenAnEmptyString_whenCallingWrap_thenResultIsAnEmptyString() {
        assertThat(StringUtilsEnum.wrap("", "wrapper")).isEmpty();
    }

    @Test
    void givenNonEmptyString_whenCallingWrap_thenResultIsWrappedString() {
        assertThat(StringUtilsEnum.wrap("asd", "wrapper")).isEqualTo("wrapperasdwrapper");
    }

}
