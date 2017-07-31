package com.baeldung.vavr.patternmatching;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.exists;
import static io.vavr.Predicates.forAll;
import static io.vavr.Predicates.instanceOf;
import static org.junit.Assert.assertEquals;

import static com.baeldung.vavr.patternmatching.BookDecompositionPatterns.*;

import java.util.function.Predicate;

import org.junit.Test;

import io.vavr.collection.List;

public class PatternMatchingWithPredicateAndObjectDecompositionUnitTest {

    @Test
    public void givenInteger_WhenMatch_ThenReturnStringValue() {
        int i = 0;
        String s = Match(i).of(Case($(0), "hello"), Case($(1), "goodbye"), Case($(), "other"));
        assertEquals("hello", s);
    }

    @Test
    public void givenInteger_WhenNoMatch_ThenReturnDefaultValue() {
        int i = 10;
        String s = Match(i).of(Case($(0), "hello"), Case($(1), "goodbye"), Case($(), "other"));
        assertEquals("other", s);
    }

    @Test
    public void givenInteger_WhenMatchWithPredicate_ThenReturnRightValue() {
        int i = 10;
        String s = Match(i).of(Case($(x -> x % 2 == 0), "even"), Case($(x -> x % 2 > 0), "odd"), Case($(), "other"));
        assertEquals("even", s);
    }

    @Test
    public void givenANumber_WhenMatchWithTypeUsingBuildInPredicate_ThenReturnRightValueForEachType() {
        Object i = 10d;
        String type = Match(i).of(Case($(instanceOf(Double.class)), "Double type"), Case($(instanceOf(Integer.class)), "Integer type"), Case($(), "other type"));
        assertEquals("Double type", type);
    }

    @Test
    public void givenANumber_WhenMatchWithTypeUsingPredicate_ThenReturnRightValueForEachType() {
        Object i = 10;
        String type = Match(i).of(Case($(x -> Double.class.isInstance(x)), "Double type"), Case($(x -> Integer.class.isInstance(x)), "Integer type"), Case($(), "other type"));
        assertEquals("Integer type", type);
    }

    @Test
    public void givenAList_WhenExistAPositiveNumber_ThenReturnTrue() {
        List<Integer> numbers = List.ofAll(-1, 0, 1, 2, 3);
        Predicate<Integer> p = i -> i > 0;
        String result = Match(numbers).of(Case($(exists(p)), "true"), Case($(), "false"));
        assertEquals("true", result);
    }

    @Test
    public void givenAList_WhenNoExistAnyPositiveNumber_ThenReturnFalse() {
        List<Integer> numbers = List.ofAll(0, 1, 2, 3);
        Predicate<Integer> p = i -> i < 0;
        String result = Match(numbers).of(Case($(exists(p)), "true"), Case($(), "false"));
        assertEquals("false", result);
    }

    @Test
    public void givenAList_WhenAllIsEven_ThenReturnTrue() {
        List<Integer> numbers = List.ofAll(0, 2, 4, 6);
        Predicate<Integer> p = i -> i % 2 == 0;
        String result = Match(numbers).of(Case($(forAll(p)), "true"), Case($(), "false"));
        assertEquals("true", result);
    }

    @Test
    public void givenBook_whenMatchBookWithCategoryIsHistory_thenReturnResult() {
        Book book = new Book("history", "Animals");
        String result = Match(book).of(Case($Book($("history"), $()), (category, name) -> "There is a historical book of " + name), Case($(), () -> "not found"));
        assertEquals("There is a historical book of Animals", result);
    }
}
