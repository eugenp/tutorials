package com.baeldung.initials;

import static com.baeldung.initials.InitialFinder.getInitialUsingLoop;
import static com.baeldung.initials.InitialFinder.getInitialUsingRegex;
import static com.baeldung.initials.InitialFinder.getInitialUsingStreamsAPI;
import static com.baeldung.initials.InitialFinder.getInitialUsingStringTokenizer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GetInitialsFromNameUnitTest {

    @ParameterizedTest
    @CsvSource({"John F Kennedy,JFK", ",''", "'',''", "Not Correct   88text,NC", "michael jackson,MJ", "123,''", "123 234A,''", "1test 2test, ''"})
    public void getInitialFromName_usingLoop(String input, String expected) {
        String initial = getInitialUsingLoop(input);
        assertEquals(expected, initial);
    }

    @ParameterizedTest
    @CsvSource({"John F Kennedy,JFK", ",''", "'',''", "Not Correct   88text,NC", "michael jackson,MJ", "123,''", "123 234A,''", "1test 2test, ''"})
    public void getInitialFromName_usingStringTokenizer(String input, String expected) {
        String initial = getInitialUsingStringTokenizer(input);
        assertEquals(expected, initial);
    }

    @ParameterizedTest
    @CsvSource({"John F Kennedy,JFK", ",''", "'',''", "Not Correct   88text,NC", "michael jackson,MJ", "123,''", "123 234A,''", "1test 2test, ''"})
    public void getInitialFromName_usingRegex(String input, String expected) {
        String initial = getInitialUsingRegex(input);
        assertEquals(expected, initial);
    }

    @ParameterizedTest
    @CsvSource({"John F Kennedy,JFK", ",''", "'',''", "Not Correct   88text,NC", "michael jackson,MJ", "123,''", "123 234A,''", "1test 2test, ''"})
    public void getInitialFromName_usingStreamsAPI(String input, String expected) {
        String initial = getInitialUsingStreamsAPI(input);
        assertEquals(expected, initial);
    }
}

class InitialFinder {
    public static String getInitialUsingLoop(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        String[] parts = name.split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (part.matches("[a-zA-Z].*")) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }

    public static String getInitialUsingStringTokenizer(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        StringTokenizer tokenizer = new StringTokenizer(name);
        StringBuilder initials = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            String part = tokenizer.nextToken();
            if (part.matches("[a-zA-Z].*")) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }

    public static String getInitialUsingRegex(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
        Matcher matcher = pattern.matcher(name);
        StringBuilder initials = new StringBuilder();
        while (matcher.find()) {
            initials.append(matcher.group());
        }
        return initials.toString().toUpperCase();
    }

    public static String getInitialUsingStreamsAPI(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return Arrays.stream(name.split("\\s+"))
                .filter(part -> part.matches("[a-zA-Z].*"))
                .map(part -> part.substring(0, 1))
                .collect(Collectors.joining())
                .toUpperCase();
    }
}