package com.baeldung.trim;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.google.common.base.CharMatcher;

/**
 * Based on https://github.com/tedyoung/indexof-contains-benchmark
 */
@Fork(5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LTrimRTrim {

    private String src;
    private static String ltrimResult;
    private static String rtrimResult;
    private static Pattern LTRIM = Pattern.compile("^\\s+");
    private static Pattern RTRIM = Pattern.compile("\\s+$");

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() {
        src = "       White spaces left and right          ";
        ltrimResult = "White spaces left and right          ";
        rtrimResult = "       White spaces left and right";
    }

    public static String whileLtrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }

    public static String whileRtrim(String s) {
        int i = s.length() - 1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0, i + 1);
    }

    private static boolean checkStrings(String ltrim, String rtrim) {
        boolean result = false;

        if (ltrimResult.equalsIgnoreCase(ltrim) && rtrimResult.equalsIgnoreCase(rtrim))
            result = true;

        return result;
    }

    // Going through the String detecting Whitespaces
    @Benchmark
    public boolean whileCharacters() {
        String ltrim = whileLtrim(src);
        String rtrim = whileRtrim(src);

        return checkStrings(ltrim, rtrim);
    }

    // replaceAll() and Regular Expressions
    @Benchmark
    public boolean replaceAllRegularExpression() {
        String ltrim = src.replaceAll("^\\s+", "");
        String rtrim = src.replaceAll("\\s+$", "");

        return checkStrings(ltrim, rtrim);
    }

    public static String patternLtrim(String s) {
        return LTRIM.matcher(s)
            .replaceAll("");
    }

    public static String patternRtrim(String s) {
        return RTRIM.matcher(s)
            .replaceAll("");
    }

    // Pattern matches() with replaceAll
    @Benchmark
    public boolean patternMatchesLTtrimRTrim() {
        String ltrim = patternLtrim(src);
        String rtrim = patternRtrim(src);

        return checkStrings(ltrim, rtrim);
    }

    // Guava CharMatcher trimLeadingFrom / trimTrailingFrom
    @Benchmark
    public boolean guavaCharMatcher() {
        String ltrim = CharMatcher.whitespace().trimLeadingFrom(src);
        String rtrim = CharMatcher.whitespace().trimTrailingFrom(src);

        return checkStrings(ltrim, rtrim);
    }

    // Apache Commons StringUtils containsIgnoreCase
    @Benchmark
    public boolean apacheCommonsStringUtils() {
        String ltrim = org.apache.commons.lang3.StringUtils.stripStart(src, null);
        String rtrim = org.apache.commons.lang3.StringUtils.stripEnd(src, null);

        return checkStrings(ltrim, rtrim);
    }

}
