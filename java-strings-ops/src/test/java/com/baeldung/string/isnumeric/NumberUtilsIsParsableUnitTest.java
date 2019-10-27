package com.baeldung.string.isnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumberUtilsIsParsableUnitTest {
    @Test
    public void givenApacheCommons_whenUsingIsParsable_thenTrue() {
        // Valid Numbers
        assertThat(NumberUtils.isParsable("22")).isTrue();
        assertThat(NumberUtils.isParsable("-23")).isTrue();
        assertThat(NumberUtils.isParsable("2.2")).isTrue();
        assertThat(NumberUtils.isParsable("09")).isTrue();

        // Invalid Numbers
        assertThat(NumberUtils.isParsable(null)).isFalse();
        assertThat(NumberUtils.isParsable("")).isFalse();
        assertThat(NumberUtils.isParsable("6.2f")).isFalse();
        assertThat(NumberUtils.isParsable("9.8d")).isFalse();
        assertThat(NumberUtils.isParsable("22L")).isFalse();
        assertThat(NumberUtils.isParsable("0xFF")).isFalse();
        assertThat(NumberUtils.isParsable("2.99e+8")).isFalse();
    }
}
