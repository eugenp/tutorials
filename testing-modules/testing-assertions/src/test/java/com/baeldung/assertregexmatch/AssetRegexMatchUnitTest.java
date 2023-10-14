package com.baeldung.assertregexmatch;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

public class AssetRegexMatchUnitTest {

    @Test
    void whenUsingJunit5assertTrue_thenGetExpectedResult() {
        assertTrue("Java at Baeldung".matches(".* at Baeldung$"));
        assertFalse("something else".matches(".* at Baeldung$"));
    }

    @Test
    void whenUsingJunit5assertLinesMatch_thenGetExpectedResult() {
        assertLinesMatch(List.of(".* at Baeldung$"), List.of("Kotlin at Baeldung"));
    }

    @Test
    void whenUsingJunit5assertLinesMatch_thenEqualsIsCheckedFirst() {
        assertFalse(".* at Baeldung$".matches(".* at Baeldung$"));
        assertLinesMatch(List.of(".* at Baeldung$"), List.of(".* at Baeldung$"));
    }

    @Test
    void whenUsingAssertJMatches_thenGetExpectedResult() {
        org.assertj.core.api.Assertions.assertThat("Linux at Baeldung").matches(".* at Baeldung$");
        org.assertj.core.api.Assertions.assertThat("something unrelated").doesNotMatch(".* at Baeldung$");
    }

    @Test
    void whenUsingHamcrestMatches_thenGetExpectedResult() {
        org.hamcrest.MatcherAssert.assertThat("Computer science at Baeldung", matchesPattern(".* at Baeldung$"));
        org.hamcrest.MatcherAssert.assertThat("something unrelated", not(matchesPattern(".* at Baeldung$")));
    }

}