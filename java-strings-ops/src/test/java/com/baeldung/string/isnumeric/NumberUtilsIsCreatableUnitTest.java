package com.baeldung.string.isnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumberUtilsIsCreatableUnitTest {
    @Test
    public void givenApacheCommons_whenUsingIsParsable_thenTrue() {
        // Valid Numbers
        assertThat(NumberUtils.isCreatable("22")).isTrue();
        assertThat(NumberUtils.isCreatable("5.05")).isTrue();
        assertThat(NumberUtils.isCreatable("-200")).isTrue();
        assertThat(NumberUtils.isCreatable("10.0d")).isTrue();
        assertThat(NumberUtils.isCreatable("1000L")).isTrue();
        assertThat(NumberUtils.isCreatable("0xFF")).isTrue();
        assertThat(NumberUtils.isCreatable("07")).isTrue();
        assertThat(NumberUtils.isCreatable("2.99e+8")).isTrue();

        // Invalid Numbers
        assertThat(NumberUtils.isCreatable(null)).isFalse();
        assertThat(NumberUtils.isCreatable("")).isFalse();
        assertThat(NumberUtils.isCreatable("abc")).isFalse();
        assertThat(NumberUtils.isCreatable(" 22 ")).isFalse();
        assertThat(NumberUtils.isCreatable("09")).isFalse();
    }
}
