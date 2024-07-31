package com.baeldung.skippingfirstelement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@EnabledForJreRange(min = JRE.JAVA_9)
class SkipFirstElementExampleUnitTest {

    private static TestableSkipFirstElement testableSkip = new TestableSkipFirstElement();

    @BeforeEach
    void setup() {
        testableSkip.reset();
    }

    private static Stream<Arguments> listProvider() {
        return Stream.of(
            Arguments.of(
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                List.of("Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))
        );
    }

    private static Stream<Arguments> mapProvider() {
        return Stream.of(
            Arguments.of(
                Map.of(
                    "Monday", "The day when coffee is a life support system.",
                    "Tuesday", "The day you realize that Monday's optimism was a lie.",
                    "Wednesday", "Hump Day, or as it's known, the 'Is it Friday yet?' day.",
                    "Thursday", "The day that says, 'Hold my beer, Friday is coming!'",
                    "Friday", "The golden child of the weekdays. The superhero of the workweek.",
                    "Saturday", "The day of rest? More like the day of 'What can I binge-watch next?'",
                    "Sunday", "The day before you have to adult again."
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithForLoop(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithForLoop(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithWhileLoop(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithWhileLoop(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInSetWithWhileLoop(List<String> input) {
        testableSkip.skippingFirstElementInSetWithWhileLoop(new HashSet<>(input));
        Set<?> actual = new HashSet<>(testableSkip.getResult());
        assertEquals(actual.size(), input.size() - 1);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithWhileLoopStoringFirstElement(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithWhileLoopStoringFirstElement(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("mapProvider")
    void skippingFirstElementInMapWithStreamSkip(Map<String, String> input) {
        testableSkip.skippingFirstElementInMapWithStreamSkip(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual.size(), input.size() - 1);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithSubList(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithSubList(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithForLoopWithAdditionalCheck(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithForLoopWithAdditionalCheck(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithWhileLoopWithCounter(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithWhileLoopWithCounter(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void skippingFirstElementInListWithReduce(List<String> input, List<String> expected) {
        testableSkip.skippingFirstElementInListWithReduce(input);
        List<?> actual = testableSkip.getResult();
        assertEquals(actual, expected);
    }
}