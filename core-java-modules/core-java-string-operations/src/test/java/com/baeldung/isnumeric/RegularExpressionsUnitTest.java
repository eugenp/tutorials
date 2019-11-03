package com.baeldung.isnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RegularExpressionsUnitTest {
    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    @Test
    public void whenUsingRegularExpressions_thenTrue() {
        // Valid Numbers
        assertThat(isNumeric("22")).isTrue();
        assertThat(isNumeric("5.05")).isTrue();
        assertThat(isNumeric("-200")).isTrue();

        // Invalid Numbers
        assertThat(isNumeric("abc")).isFalse();
    }
}
