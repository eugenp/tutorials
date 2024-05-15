package com.baeldung.displayname;

import java.util.stream.Stream;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParameterizedTestNameGeneratorUnitTest {

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    void whenUsingDefaultAttributes_thenGenerateDefaultDisplayNames(String givenArg) {
        // Test
    }

    @ParameterizedTest(name = "Parameter with index {index} => {0}")
    @MethodSource("argumentsProvider")
    void whenUsingNameAttribute_thenGenerateCustomDisplayNames(String givenArg) {
        // Test
    }

    @ParameterizedTest
    @MethodSource("namedArguments")
    void whenUsingNamedInterface_thenGenerateCustomDisplayNames(String givenArg) {
        // Test
    }

    private static Stream<Arguments> argumentsProvider() {
        return Stream.of(Arguments.of("City: Madrid"), Arguments.of("Country: Spain"), Arguments.of("Continent: Europe"));
    }

    private static Stream<Arguments> namedArguments() {
        return Stream.of(Arguments.of(Named.of("Testing with a city", "Tokyo")), Arguments.of(Named.of("Testing with a country", "Japan")), Arguments.of(Named.of("Testing with a continent", "Asia")));
    }
}
