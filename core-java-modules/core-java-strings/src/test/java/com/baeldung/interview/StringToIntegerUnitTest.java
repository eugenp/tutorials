package com.baeldung.interview;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringToIntegerUnitTest {
    @Test
    public void givenString_whenParsingInt_shouldConvertToInt() {
        String givenString = "42";

        int result = Integer.parseInt(givenString);

        assertThat(result).isEqualTo(42);
    }
}
