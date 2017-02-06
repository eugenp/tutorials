package com.baeldung;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.primitives.Ints;

public class StringToIntOrIntegerUnitTest {

    @Test
    public void givenString_whenParsingInt_shouldConvertToInt() {
        String givenString = "42";

        int result = Integer.parseInt(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void givenString_whenCallingIntegerValueOf_shouldConvertToInt() {
        String givenString = "42";

        Integer result = Integer.valueOf(givenString);

        assertThat(result).isEqualTo(new Integer(42));
    }

    @Test
    public void givenString_whenCallingIntegerConstructor_shouldConvertToInt() {
        String givenString = "42";

        Integer result = new Integer(givenString);

        assertThat(result).isEqualTo(new Integer(42));
    }

    @Test
    public void givenString_whenCallingIntegerDecode_shouldConvertToInt() {
        String givenString = "42";

        int result = Integer.decode(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void givenString_whenTryParse_shouldConvertToInt() {
        String givenString = "42";

        Integer result = Ints.tryParse(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidInput_whenParsingInt_shouldThrow() {
        String givenString = "nan";
        Integer.parseInt(givenString);
    }

}
