package com.baeldung.vavr;

import io.vavr.MatchError;
import io.vavr.control.Option;
import org.junit.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Predicates.allOf;
import static io.vavr.Predicates.anyOf;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;
import static io.vavr.Predicates.isNotNull;
import static io.vavr.Predicates.isNull;
import static io.vavr.Predicates.noneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatternMatchingUnitTest {
    @Test
    public void whenMatchesDefault_thenCorrect() {
        int input = 5;
        String output = Match(input).of(Case($(1), "one"), Case($(2), "two"), Case($(3), "three"), Case($(), "unknown"));

        assertEquals("unknown", output);
    }

    @Test(expected = MatchError.class)
    public void givenNoMatchAndNoDefault_whenThrows_thenCorrect() {
        int input = 5;
        Match(input).of(Case($(1), "one"), Case($(2), "two"));
    }

    @Test
    public void whenMatchWorksWithOption_thenCorrect() {
        int i = 10;
        Option<String> s = Match(i).option(Case($(0), "zero"));
        assertTrue(s.isEmpty());
        assertEquals("None", s.toString());
    }

    @Test
    public void whenMatchWorksWithPredicate_thenCorrect() {
        int i = 3;
        String s = Match(i).of(Case($(is(1)), "one"), Case($(is(2)), "two"), Case($(is(3)), "three"), Case($(), "?"));
        assertEquals("three", s);
    }

    @Test
    public void givenInput_whenMatchesClass_thenCorrect() {
        Object obj = 5;
        String s = Match(obj).of(Case($(instanceOf(String.class)), "string matched"), Case($(), "not string"));
        assertEquals("not string", s);
    }

    @Test
    public void givenInput_whenMatchesNull_thenCorrect() {
        Object obj = 5;
        String s = Match(obj).of(Case($(isNull()), "no value"), Case($(isNotNull()), "value found"));
        assertEquals("value found", s);
    }

    @Test
    public void givenInput_whenContainsWorks_thenCorrect() {
        int i = 5;
        String s = Match(i).of(Case($(isIn(2, 4, 6, 8)), "Even Single Digit"), Case($(isIn(1, 3, 5, 7, 9)), "Odd Single Digit"), Case($(), "Out of range"));
        assertEquals("Odd Single Digit", s);
    }

    @Test
    public void givenInput_whenMatchAllWorks_thenCorrect() {
        Integer i = null;
        String s = Match(i).of(Case($(allOf(isNotNull(), isIn(1, 2, 3, null))), "Number found"), Case($(), "Not found"));
        assertEquals("Not found", s);
    }

    @Test
    public void givenInput_whenMatchesAnyOfWorks_thenCorrect() {
        Integer year = 1990;
        String s = Match(year).of(Case($(anyOf(isIn(1990, 1991, 1992), is(1986))), "Age match"), Case($(), "No age match"));
        assertEquals("Age match", s);
    }

    @Test
    public void givenInput_whenMatchesNoneOfWorks_thenCorrect() {
        Integer year = 1990;
        String s = Match(year).of(Case($(noneOf(isIn(1990, 1991, 1992), is(1986))), "Age match"), Case($(), "No age match"));
        assertEquals("No age match", s);
    }

    @Test
    public void whenMatchWorksWithPredicate_thenCorrect2() {
        int i = 5;
        String s = Match(i).of(Case($(isIn(2, 4, 6, 8)), "Even Single Digit"), Case($(isIn(1, 3, 5, 7, 9)), "Odd Single Digit"), Case($(), "Out of range"));
        assertEquals("Odd Single Digit", s);
    }

    @Test
    public void whenMatchCreatesSideEffects_thenCorrect() {
        int i = 4;
        Match(i).of(Case($(isIn(2, 4, 6, 8)), o -> run(this::displayEven)), Case($(isIn(1, 3, 5, 7, 9)), o -> run(this::displayOdd)), Case($(), o -> run(() -> {
            throw new IllegalArgumentException(String.valueOf(i));
        })));
    }

    private void displayEven() {
        System.out.println("Input is even");
    }

    private void displayOdd() {
        System.out.println("Input is odd");
    }
}
