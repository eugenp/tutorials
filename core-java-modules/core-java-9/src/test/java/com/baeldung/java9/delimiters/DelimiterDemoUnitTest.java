package com.baeldung.java9.delimiters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class DelimiterDemoUnitTest {

    @Test
    void givenSimpleCharacterDelimiter_whenScannerWithDelimiter_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiter, "Welcome to Baeldung", "\\s", Arrays.asList("Welcome", "to", "Baeldung"));
    }

    @Test
    void givenStringDelimiter_whenScannerWithDelimiter_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiter, "HelloBaeldungHelloWorld", "Hello", Arrays.asList("Baeldung", "World"));
    }

    @Test
    void givenVariousPossibleDelimiters_whenScannerWithDelimiter_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiter, "Welcome to Baeldung.\nThank you for reading.\nThe team", "\n|\\s", Arrays.asList("Welcome", "to", "Baeldung.", "Thank", "you", "for", "reading.", "The", "team"));
    }

    @Test
    void givenWildcardRegexDelimiter_whenScannerWithDelimiter_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiter, "1aaaaaaa2aa3aaa4", "a+", Arrays.asList("1", "2", "3", "4"));
    }

    @Test
    void givenSimpleCharacterDelimiter_whenScannerWithDelimiterUsingPattern_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiterUsingPattern, "Welcome to Baeldung", Pattern.compile("\\s"), Arrays.asList("Welcome", "to", "Baeldung"));
    }

    @Test
    void givenStringDelimiter_whenScannerWithDelimiterUsingPattern_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiterUsingPattern, "HelloBaeldungHelloWorld", Pattern.compile("Hello"), Arrays.asList("Baeldung", "World"));
    }

    @Test
    void givenVariousPossibleDelimiters_whenScannerWithDelimiterUsingPattern_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiterUsingPattern, "Welcome to Baeldung.\nThank you for reading.\nThe team", Pattern.compile("\n|\\s"), Arrays.asList("Welcome", "to", "Baeldung.", "Thank", "you", "for", "reading.", "The", "team"));
    }

    @Test
    void givenWildcardRegexDelimiters_whenScannerWithDelimiterUsingPattern_ThenInputIsCorrectlyParsed() {
        checkOutput(DelimiterDemo::scannerWithDelimiterUsingPattern, "1aaaaaaa2aa3aaa4", Pattern.compile("a*"), Arrays.asList("1", "2", "3", "4"));
    }

    void checkOutput(BiFunction<String, String, List<String>> function, String input, String delimiter, List<String> expectedOutput) {
        assertEquals(expectedOutput, function.apply(input, delimiter));
    }

    void checkOutput(BiFunction<String, Pattern, List<String>> function, String input, Pattern delimiter, List<String> expectedOutput) {
        assertEquals(expectedOutput, function.apply(input, delimiter));
    }

    @Test
    void whenBaseScanner_ThenWhitespacesAreUsedAsDelimiters() {
        assertEquals(List.of("Welcome", "at", "Baeldung"), DelimiterDemo.baseScanner("Welcome at Baeldung"));
    }

}
