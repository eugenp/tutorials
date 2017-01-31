package com.baeldung;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StringToCharArrayUnitTest {

    @Test
    public void givenString_whenCallingToArray_shouldConvertToCharArray() {

        String givenString = "first blog";

        char[] charArray = givenString.toCharArray();

        assertThat(charArray.length).isEqualTo(givenString.length());
    }
}
