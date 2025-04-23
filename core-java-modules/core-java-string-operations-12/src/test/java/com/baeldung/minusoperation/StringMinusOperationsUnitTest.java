package com.baeldung.minusoperation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringMinusOperationsUnitTest {

    @Test
    public void givenNotBlankString_whenRemovingLastChar_thenReturnOriginalWithoutLastChar() {
        var original = "Don't give up!";

        var result = StringMinusOperations.removeLastCharBySubstring(original);

        assertThat(result)
            .doesNotContain("!")
            .isEqualTo("Don't give up"); // no '!' at the end
    }

    @Test
    public void givenNotBlankString_whenRemovingSpecificChar_thenReturnOriginalWithoutThatChar() {
        var original = "Don't give up!";
        var toRemove = 't';

        var result = StringMinusOperations.minusByReplace(original, toRemove);

        assertThat(result)
            .doesNotContain(String.valueOf(toRemove))
            .isEqualTo("Don' give up!"); // no 't'
    }

    @Test
    public void givenNotBlankString_whenRemovingSpecificString_thenReturnOriginalWithoutThatString() {
        var original = "Don't give up!";
        var toRemove = "Don't";

        var result = StringMinusOperations.minusByReplace(original, toRemove);

        assertThat(result)
            .doesNotContain(toRemove)
            .isEqualTo(" give up!"); // no 'Don't'
    }

    @Test
    public void givenNotBlankString_whenRemovingSpecificStringByStream_thenReturnOriginalWithoutThatString() {
        var original = "Don't give up!";
        var toRemove = ' ';

        var result = StringMinusOperations.minusByStream(original, toRemove);

        assertThat(result)
            .doesNotContain(String.valueOf(toRemove))
            .isEqualTo("Don'tgiveup!"); // no blanks
    }
}
