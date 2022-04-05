package com.baeldung.substringsearch;

import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Based on https://github.com/tedyoung/indexof-contains-benchmark
 */
@Fork(5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SubstringSearchPerformanceComparison {

    private String message;

    private Pattern pattern;

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() {
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
        pattern = Pattern.compile("(?<!\\S)" + "eiusmod" + "(?!\\S)");
    }

    @Benchmark
    public int indexOf() {
        return message.indexOf("eiusmod");
    }

    @Benchmark
    public boolean contains() {
        return message.contains("eiusmod");
    }

    @Benchmark
    public boolean containsStringUtilsIgnoreCase() {
        return StringUtils.containsIgnoreCase(message, "eiusmod");
    }

    @Benchmark
    public boolean searchWithPattern() {
        return pattern.matcher(message).find();
    }
}