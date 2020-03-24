package com.baeldung.chartostring;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CharToStringUnitTest {

    @Test
    public void givenChar_whenCallingStringValueOf_shouldConvertToString() {
        final char givenChar = 'x';

        final String result = String.valueOf(givenChar);

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenCallingToStringOnCharacter_shouldConvertToString() {
        final char givenChar = 'x';

        final String result = Character.toString(givenChar);

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenCallingCharacterConstructor_shouldConvertToString3() {
        final char givenChar = 'x';

        final String result = new Character(givenChar).toString();

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenConcatenated_shouldConvertToString4() {
        final char givenChar = 'x';

        final String result = givenChar + "";

        assertThat(result).isEqualTo("x");
    }

    @Test
    public void givenChar_whenFormated_shouldConvertToString5() {
        final char givenChar = 'x';

        final String result = String.format("%c", givenChar);

        assertThat(result).isEqualTo("x");
    }
}
