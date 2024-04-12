package com.baeldung.skippingelements;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        final List<String> sourceList = input.collect(Collectors.toList());
        final List<String> actual = IntStream.range(0, sourceList.size())
            .filter(s -> (s + 1) % n == 0)
            .mapToObj(sourceList::get)
            .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithIterateTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> sourceList = input.collect(Collectors.toList());
        int limit = sourceList.size() / n;
        final List<String> actual = IntStream.iterate(n - 1, i -> (i + n))
            .limit(limit)
            .mapToObj(sourceList::get)
            .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithSublistTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> sourceList = input.collect(Collectors.toList());
        int limit = sourceList.size() / n;
        final List<String> actual = Stream.iterate(sourceList, s -> s.subList(n, s.size()))
            .limit(limit)
            .map(s -> s.get(n - 1))
            .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInListWithForTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> sourceList = input.collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (int i = n - 1; i < sourceList.size(); i += n) {
            result.add(sourceList.get(i));
        }
        final List<String> actual = result;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInStreamWithIteratorTestShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        List<String> result = new ArrayList<>();
        final Iterator<String> iterator = input.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            if (count % n == n - 1) {
                result.add(iterator.next());
            } else {
                iterator.next();
            }
            ++count;
        }
        final List<String> actual = result;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void givenListSkipNthElementInStreamWithCollectorShouldFilterNthElement(Stream<String> input, List<String> expected, int n) {
        final List<String> actual = input.collect(SkippingCollector.collector(n));
        assertEquals(expected, actual);
    }
}