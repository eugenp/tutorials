package com.baeldung.skippingelements;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SkippingElementsUnitTest {

    private static Stream<Arguments> testSource() {
        return Stream.of(
            Arguments.of(
                Stream.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
                    "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty One", "Twenty Two",
                    "Twenty Three", "Twenty Four", "Twenty Five", "Twenty Six", "Twenty Seven", "Twenty Eight", "Twenty Nine", "Thirty",
                    "Thirty One", "Thirty Two", "Thirty Three"),
                List.of("Three", "Six", "Nine", "Twelve", "Fifteen", "Eighteen", "Twenty One", "Twenty Four", "Twenty Seven", "Thirty",
                    "Thirty Three"),
                3),
            Arguments.of(
                Stream.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
                    "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty One", "Twenty Two",
                    "Twenty Three", "Twenty Four", "Twenty Five", "Twenty Six", "Twenty Seven", "Twenty Eight", "Twenty Nine", "Thirty",
                    "Thirty One", "Thirty Two", "Thirty Three"),
                List.of("Five", "Ten", "Fifteen", "Twenty", "Twenty Five", "Thirty"),
                5),
            Arguments.of(
                Stream.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
                    "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty One", "Twenty Two",
                    "Twenty Three", "Twenty Four", "Twenty Five", "Twenty Six", "Twenty Seven", "Twenty Eight", "Twenty Nine", "Thirty",
                    "Thirty One", "Thirty Two", "Thirty Three"),
                List.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
                    "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty One", "Twenty Two",
                    "Twenty Three", "Twenty Four", "Twenty Five", "Twenty Six", "Twenty Seven", "Twenty Eight", "Twenty Nine", "Thirty",
                    "Thirty One", "Thirty Two", "Thirty Three"),
                1),
            Arguments.of(
                Stream.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                List.of("Wednesday", "Saturday"),
                3),
            Arguments.of(
                Stream.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                List.of("Friday"),
                5),
            Arguments.of(
                Stream.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                1),
            Arguments.of(
                Stream.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
                    "December"),
                List.of("March", "June", "September", "December"),
                3),
            Arguments.of(
                Stream.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
                    "December"),
                List.of("May", "October"),
                5),
            Arguments.of(
                Stream.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
                    "December"),
                List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
                    "December"),
                1)
        );
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithFilterTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInListWithFilter(input, n);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithIterateTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInListWithIterate(input, n);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithSublistTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInListWithSublist(input, n);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithForTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInListWithFor(input, n);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInStreamWithIteratorTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInListWithIterator(input, n);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInStreamWithCollectorShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = SkippingElements.skipNthElementInStreamWithCollector(input, n);
        assertEquals(expected, actual);
    }
}