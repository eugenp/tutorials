package com.baeldung.optional;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalChainingUnitTest {

    private boolean getEmptyEvaluated;
    private boolean getHelloEvaluated;
    private boolean getByeEvaluated;

    @Before
    public void setUp() {
        getEmptyEvaluated = false;
        getHelloEvaluated = false;
        getByeEvaluated = false;
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.of(getEmpty(), getHello(), getBye())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(getHello(), found);
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenEmptyOptionalIsReturned() {
        Optional<String> found = Stream.of(getEmpty(), getEmpty())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertFalse(found.isPresent());
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenDefaultIsReturned() {
        String found = Stream.<Supplier<Optional<String>>>of(
                () -> createOptional("empty"),
                () -> createOptional("empty")
        )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseGet(() -> "default");

        assertEquals("default", found);
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(this::getEmpty, this::getHello, this::getBye)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertTrue(this.getEmptyEvaluated);
        assertTrue(this.getHelloEvaluated);
        assertFalse(this.getByeEvaluated);
        assertEquals(getHello(), found);
    }

    @Test
    public void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(
                () -> createOptional("empty"),
                () -> createOptional("hello")
        )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(createOptional("hello"), found);
    }

    private Optional<String> getEmpty() {
        this.getEmptyEvaluated = true;
        return Optional.empty();
    }

    private Optional<String> getHello() {
        this.getHelloEvaluated = true;
        return Optional.of("hello");
    }

    private Optional<String> getBye() {
        this.getByeEvaluated = true;
        return Optional.of("bye");
    }

    private Optional<String> createOptional(String input) {
        if (input == null || "".equals(input) || "empty".equals(input)) {
            return Optional.empty();
        }

        return Optional.of(input);
    }
}