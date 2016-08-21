package com.baeldung;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CharToStringTest {

    @Test
    public void givenChar_shouldConvertToString1() throws Exception {
        final char givenChar = 'x';

        final String result = String.valueOf(givenChar);

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_shouldConvertToString2() throws Exception {
        final char givenChar = 'x';

        final String result = Character.toString(givenChar);

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_shouldConvertToString3() throws Exception {
        final char givenChar = 'x';

        final String result = new Character(givenChar).toString();

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_shouldConvertToString4() throws Exception {
        final char givenChar = 'x';

        final String result = givenChar + "";

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_shouldConvertToString5() throws Exception {
        final char givenChar = 'x';

        final String result = String.format("%c", givenChar);

        assertThat(result).isEqualTo("x");
    }
}
