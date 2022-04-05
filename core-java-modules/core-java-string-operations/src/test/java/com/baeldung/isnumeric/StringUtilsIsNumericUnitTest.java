package com.baeldung.isnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilsIsNumericUnitTest {
    @Test
    public void givenApacheCommons_whenUsingIsNumeric_thenTrue() {
        // Valid Numbers
        assertThat(StringUtils.isNumeric("123")).isTrue();
        assertThat(StringUtils.isNumeric("١٢٣")).isTrue();
        assertThat(StringUtils.isNumeric("१२३")).isTrue();

        // Invalid Numbers
        assertThat(StringUtils.isNumeric(null)).isFalse();
        assertThat(StringUtils.isNumeric("")).isFalse();
        assertThat(StringUtils.isNumeric("  ")).isFalse();
        assertThat(StringUtils.isNumeric("12 3")).isFalse();
        assertThat(StringUtils.isNumeric("ab2c")).isFalse();
        assertThat(StringUtils.isNumeric("12.3")).isFalse();
        assertThat(StringUtils.isNumeric("-123")).isFalse();
    }
}
