package com.baeldung.junit5.parameterized;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RepeatableArgumentSourceAnnotationsUnitTest {

    static List<String> asia() {
        return Arrays.asList("Japan", "India", "Thailand");
    }

    static List<String> europe() {
        return Arrays.asList("Spain", "Italy", "England");
    }

    @ParameterizedTest
    @MethodSource("asia")
    @MethodSource("europe")
    void stringLengthCheck_ShouldReturnTrueWhenTheCountryLengthIsLargerThanThreeCharacters(String country) {
        assertTrue(country.length() > 3);
    }

}
