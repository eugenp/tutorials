package com.baeldung.backtracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BacktrackingTest {

    private static Stream<Arguments> equationsWithNoSolutions() {
        return Stream.of(Arguments.of("3456237490", 9191), Arguments.of("5", 0));
    }

    @ParameterizedTest
    @MethodSource("equationsWithNoSolutions")
    void givenEquationsWithNoSolutions_whenProcess_thenEmptyListIsReturned(String digits, int target) {
        final List<String> result = Backtracking.process(digits, target);
        assertTrue(result.isEmpty());
    }

    private static Stream<Arguments> equationsWithValidSolutions() {
        return Stream.of(Arguments.of("123", 6, Arrays.asList("1+2+3", "1*2*3")), Arguments.of("232", 8, Arrays.asList("2*3+2", "2+3*2")),
            Arguments.of("1010", 20, Collections.singletonList("10+10")));
    }

    @ParameterizedTest
    @MethodSource("equationsWithValidSolutions")
    void givenEquationsWithValidSolutions_whenProcess_thenValidResultsAreReturned(String digits, int target, List<String> expectedSolutions) {
        final List<String> result = Backtracking.process(digits, target);

        assertEquals(expectedSolutions.size(), result.size());

        expectedSolutions.stream()
            .map(result::contains)
            .forEach(Assertions::assertTrue);
    }

}
