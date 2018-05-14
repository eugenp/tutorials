
package com.baeldung.optional;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import com.baeldung.java9.optional.Optionals;

public class OptionalsTest {

    @Test
    public void givenOptional_whenEmptyValue_thenEmpty() {
        assertEquals(Optional.empty(), Optionals.getName(Optional.ofNullable(null)));
    }

    @Test
    public void givenOptional_whenValue_thenOptional() {
        String name = "Filan Fisteku";
        Optional<String> optionalString = Optional.ofNullable(name);
        assertEquals(optionalString, Optionals.getName(optionalString));
    }

    @Test
    public void givenOptional_whenValue_thenCustomOptional() {
        String name = "Filan Fisteku";
        Optional<String> optionalString = Optional.ofNullable(name);
        assertEquals(optionalString, Optionals.getNameJava8(optionalString));
    }

    @Test
    public void givenOptional_whenNull_thenEmptyOptional() {
        assertEquals(Optional.empty(), Optionals.getNameJava8(Optional.ofNullable(null)));
    }

    @Test
    public void givenGuavaOptional_whenInvoke_thenOptional() {
        String name = "Filan Fisteku";
        com.google.common.base.Optional<String> stringOptional = com.google.common.base.Optional.of(name);
        assertEquals(stringOptional, Optionals.getOptionalGuavaName(stringOptional));
    }

    @Test
    public void givenGuavaOptional_whenNull_thenDefaultText() {
        assertEquals(com.google.common.base.Optional.absent(), Optionals.getOptionalGuavaName(com.google.common.base.Optional.fromNullable(null)));
    }
}