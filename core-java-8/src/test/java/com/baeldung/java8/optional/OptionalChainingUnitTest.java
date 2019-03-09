package com.baeldung.java8.optional;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OptionalChainingUnitTest {

    private boolean getFirstEvaluated;
    private boolean getSecondEvaluated;
    private boolean getThirdEvaluated;

    @Before
    public void setUp() {
        getFirstEvaluated = false;
        getSecondEvaluated = false;
        getThirdEvaluated = false;
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.of(getFirst(), getSecond(), getThird())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(getSecond(), found);
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenEmptyOptionalIsReturned() {
        Optional<Object> first = Optional.empty();
        Optional<Object> second = Optional.empty();

        Optional<Object> found = Stream.of(first, second)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertFalse(found.isPresent());
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenDefaultIsReturned() {
        Optional<Object> first = Optional.empty();
        Optional<Object> second = Optional.empty();

        Object found = Stream.of(first, second)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse("default");

        assertEquals("default", found);
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
        Supplier<Optional<String>> getFirstSupplier = this::getFirst;
        Supplier<Optional<String>> getSecondSupplier = this::getSecond;
        Supplier<Optional<String>> getThirdSupplier = this::getThird;

        Optional<String> found = Stream.<Supplier<Optional<String>>>of(getFirstSupplier, getSecondSupplier, getThirdSupplier)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertTrue(this.getFirstEvaluated);
        assertTrue(this.getSecondEvaluated);
        assertFalse(this.getThirdEvaluated);
        assertEquals(getSecond(), found);
    }

    @Test
    public void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(
                () -> get("empty"),
                () -> get("hello")
        )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(get("hello"), found);
    }

    private Optional<String> getFirst() {
        this.getFirstEvaluated = true;
        return Optional.empty();
    }

    private Optional<String> getSecond() {
        this.getSecondEvaluated = true;
        return Optional.of("second");
    }

    private Optional<String> getThird() {
        this.getThirdEvaluated = true;
        return Optional.of("third");
    }

    private Optional<String> get(String input) {
        if (input == null || input == "" || input == "empty") {
            return Optional.empty();
        }

        return Optional.of(input);
    }
}