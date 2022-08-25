package com.baeldung.algorithms.stringpermutation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HelperUnitTest {

    @ParameterizedTest
    @MethodSource("stringProvider")
    void toListTest(String value, List<Character> expected) {
        final List<Character> actual = Helper.toCharacterList(value);
        assertThat(expected).isEqualTo(actual);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void toStringTest(String expected, List<Character> value) {
        final String actual = Helper.toString(value);
        assertThat(expected).isEqualTo(actual);
    }

    static Stream<Arguments> stringProvider() {
        return Stream.of(
            Arguments.of("hello", Arrays.asList('h', 'e', 'l', 'l', 'o')),
            Arguments.of("abc", Arrays.asList('a','b','c')),
            Arguments.of("12345", Arrays.asList('1', '2', '3', '4', '5'))
        );
    }

}