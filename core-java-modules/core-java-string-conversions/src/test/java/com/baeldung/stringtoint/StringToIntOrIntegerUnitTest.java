package com.baeldung.stringtoint;

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
    public void givenBinaryString_whenParsingInt_shouldConvertToInt() {
        String givenString = "101010";

        int result = Integer.parseInt(givenString, 2);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void givenString_whenCallingIntegerValueOf_shouldConvertToInt() {
        String givenString = "42";

        Integer result = Integer.valueOf(givenString);

        assertThat(result).isEqualTo(new Integer(42));
    }

    @Test
    public void givenBinaryString_whenCallingIntegerValueOf_shouldConvertToInt() {
        String givenString = "101010";

        Integer result = Integer.valueOf(givenString, 2);

        assertThat(result).isEqualTo(new Integer(42));
    }
  
    @Test 
    public void givenString_whenCallingValueOf_shouldCacheSomeValues() {
        for (int i = -128; i <= 127; i++) {
            String value = i + "";
            Integer first = Integer.valueOf(value);
            Integer second = Integer.valueOf(value);

            assertThat(first).isSameAs(second);
        }
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
