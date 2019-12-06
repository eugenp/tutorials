package com.baeldung.pattern;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternPerformanceComparison {

    private static final String PATTERN = "\\d*[02468]";
    private static List<String> values;

    public static void main(String[] args) {
        loadValues();

        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        Instant start = Instant.now();
        for (String value : values) {
            value.matches(PATTERN);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms -> String.matchs(regex)");

        // Above approach "value.matches(PATTERN)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        start = Instant.now();
        for (String value : values) {
            Pattern.matches(PATTERN, value);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms -> Pattern.matches(regex, charSequence)");

        // Above approach "Pattern.matches(PATTERN, value)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        start = Instant.now();
        for (String value : values) {
            Pattern.compile(PATTERN).matcher(value).matches();
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms -> Pattern.compile(regex).matcher(charSequence).matches()");

        // With pre-compiled pattern
        // 1         Pattern object created
        // 5_000_000 Matcher objects created
        Pattern preCompiledPattern = Pattern.compile(PATTERN);
        start = Instant.now();
        for (String value : values) {
            preCompiledPattern.matcher(value).matches();
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms  -> preCompiledPattern.matcher(value).matches()");

        //With pre-compiled pattern and reusing the matcher
        // 1 Pattern object created
        // 1 Matcher objects created
        Matcher matcherFromPreCompiledPattern = preCompiledPattern.matcher("");
        start = Instant.now();
        for (String value : values) {
            matcherFromPreCompiledPattern.reset(value).matches();
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms  -> matcherFromPreCompiledPattern.reset(value).matches()");
    }

    private static void loadValues() {
        values = new ArrayList<>();
        for (int x = 1; x <= 5_000_000; x++) {
            values.add(String.valueOf(x));
        }
    }
}
