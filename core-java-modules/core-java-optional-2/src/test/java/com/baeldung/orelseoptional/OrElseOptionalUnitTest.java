package com.baeldung.orelseoptional;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class OrElseOptionalUnitTest {

    @Test
    public void givenOptional_whenValue_thenOptionalGeneralMethod() {
        String name = "Filan Fisteku";
        String missingOptional = "Name not provided";
        Optional<String> optionalString = Optional.ofNullable(name);
        Optional<String> fallbackOptionalString = Optional.ofNullable(missingOptional);
        assertEquals(optionalString, OptionalUtils.or(optionalString, fallbackOptionalString));
    }

    @Test
    public void givenEmptyOptional_whenValue_thenOptionalGeneralMethod() {
        String missingOptional = "Name not provided";
        Optional<String> optionalString = Optional.empty();
        Optional<String> fallbackOptionalString = Optional.ofNullable(missingOptional);
        assertEquals(fallbackOptionalString, OptionalUtils.or(optionalString, fallbackOptionalString));
    }

    @Test
    public void givenTwoOptionalMethods_whenFirstEmpty_thenSecondEvaluated() {
        ItemsProvider itemsProvider = new ItemsProvider();

        Optional<String> item = itemsProvider.getEmptyItem()
                .map(Optional::of)
                .orElseGet(itemsProvider::getNail);

        assertEquals(Optional.of("nail"), item);
    }

    @Test
    public void givenTwoOptionalMethods_whenFirstNonEmpty_thenSecondNotEvaluated() {
        ItemsProvider itemsProvider = new ItemsProvider();

        Optional<String> item = itemsProvider.getNail()
                .map(Optional::of)
                .orElseGet(itemsProvider::getHammer);

        assertEquals(Optional.of("nail"), item);
    }

//    Uncomment code when code base is compatible with Java 9
//    @Test
//    public void givenOptional_whenEmptyValue_thenCustomMessage() {
//        assertEquals(Optional.of("Name not provided"), OptionalUtils.getName(Optional.ofNullable(null)));
//    }
//
//    @Test
//    public void givenOptional_whenValue_thenOptional() {
//        String name = "Filan Fisteku";
//        Optional<String> optionalString = Optional.ofNullable(name);
//        assertEquals(optionalString, OptionalUtils.getName(optionalString));
//    }
//
//    @Test
//    public void givenGuavaOptional_whenInvoke_thenOptional() {
//        String name = "Filan Fisteku";
//        com.google.common.base.Optional<String> stringOptional = com.google.common.base.Optional.of(name);
//        assertEquals(stringOptional, OptionalUtils.getOptionalGuavaName(stringOptional));
//    }
//
//    @Test
//    public void givenGuavaOptional_whenNull_thenDefaultText() {
//        assertEquals(com.google.common.base.Optional.of("Name not provided"), OptionalUtils.getOptionalGuavaName(com.google.common.base.Optional.fromNullable(null)));
//    }
} 