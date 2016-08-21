package com.baeldung;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringToIntTest {

    @Test
    public void givenString_shouldConvertToInt1() throws Exception {
        String givenString = "42";

        int result = Integer.parseInt(givenString);

        assertThat(result).isEqualTo(42);
    }


    @Test
    public void givenString_shouldConvertToInt2() throws Exception {
        String givenString = "42";

        Integer result = Integer.valueOf(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void givenString_shouldConvertToInt3() throws Exception {
        String givenString = "42";

        Integer result = new Integer(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void givenString_shouldConvertToInt4() throws Exception {
        String givenString = "42";

        int result = Integer.decode(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidInput_shouldThrow() throws Exception {
        String givenString = "nan";

        int result = Integer.parseInt(givenString);
    }

}
