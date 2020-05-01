package com.baeldung.hamcrest.custommatchers;

import org.junit.Test;

import static com.baeldung.hamcrest.custommatchers.IsDivisibleBy.divisibleBy;
import static com.baeldung.hamcrest.custommatchers.IsOnlyDigits.onlyDigits;
import static com.baeldung.hamcrest.custommatchers.IsUppercase.uppercase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class HamcrestCustomUnitTest {

    @Test
    public final void givenAString_whenIsOnlyDigits_thenCorrect() {
        String digits = "123";

        assertThat(digits, is(onlyDigits()));
    }

    @Test
    public final void givenAString_whenIsNotOnlyDigits_thenCorrect() {
        String aphanumeric = "123ABC";

        assertThat(aphanumeric, is(not(onlyDigits())));
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
