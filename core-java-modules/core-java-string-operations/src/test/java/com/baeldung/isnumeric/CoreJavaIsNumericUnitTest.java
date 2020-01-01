package com.baeldung.isnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CoreJavaIsNumericUnitTest {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Test
    public void whenUsingCoreJava_thenTrue() {
        // Valid Numbers
        assertThat(isNumeric("22")).isTrue();
        assertThat(isNumeric("5.05")).isTrue();
        assertThat(isNumeric("-200")).isTrue();
        assertThat(isNumeric("10.0d")).isTrue();
        assertThat(isNumeric("   22   ")).isTrue();

        // Invalid Numbers
        assertThat(isNumeric(null)).isFalse();
        assertThat(isNumeric("")).isFalse();
        assertThat(isNumeric("abc")).isFalse();
    }
}
