package org.baeldung.hamcrest;

import org.junit.Test;

import static org.baeldung.hamcrest.custommatchers.IsDivisibleBy.divisibleBy;
import static org.baeldung.hamcrest.custommatchers.IsOnlyNumbers.onlyNumbers;
import static org.baeldung.hamcrest.custommatchers.IsUppercase.uppercase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class HamcrestCustomUnitTest {

    @Test
    public final void givenAString_whenIsOnlyNumbers_thenCorrect() {
        String numbers = "123";

        assertThat(numbers, is(onlyNumbers()));
    }

    @Test
    public final void givenAString_whenIsNotOnlyNumbers_thenCorrect() {
        String numbers = "123ABC";

        assertThat(numbers, is(not(onlyNumbers())));
    }

    @Test
    public final void givenAString_whenIsUppercase_thenCorrect() {
        String uppercaseWord = "HELLO";

        assertThat(uppercaseWord, is(uppercase()));
    }

    @Test
    public final void givenAnEvenInteger_whenDivisibleByTwo_thenCorrect() {
        Integer ten = 10;
        Integer two = 2;

        assertThat(ten, is(divisibleBy(two)));
    }

    @Test
    public final void givenAnOddInteger_whenNotDivisibleByTwo_thenCorrect() {
        Integer eleven = 11;
        Integer two = 2;

        assertThat(eleven, is(not(divisibleBy(two))));
    }
}
