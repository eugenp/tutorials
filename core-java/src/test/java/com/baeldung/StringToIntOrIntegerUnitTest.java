package com.baeldung;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.primitives.Ints;

/**
 * 测试：String->int or Integer
 * 方法：
 * （1）{@link java.lang.Integer#parseInt(String)}
 * （2）{@link java.lang.Integer#valueOf(String)}
 * （3）{@link new Integer(givenString)}
 * （4）{@link java.lang.Integer#decode(String)}
 * （5）{@link com.google.common.primitives.Ints#tryParse(String)}
 *
 */
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
