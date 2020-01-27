package com.baeldung.contains;

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

/**
 * Based on https://github.com/tedyoung/indexof-contains-benchmark
 */
@Fork(5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CaseInsensitiveWorkarounds {

    private String src;
    private String dest;
    private Pattern pattern;

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() {
        src = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
        dest = "eiusmod";
        pattern = Pattern.compile(Pattern.quote(dest), Pattern.CASE_INSENSITIVE);
    }

    // toLowerCase() and contains()
    @Benchmark
    public boolean lowerCaseContains() {
        return src.toLowerCase()
            .contains(dest.toLowerCase());
    }

    // matches() with Regular Expressions
    @Benchmark
    public boolean matchesRegularExpression() {
        return src.matches("(?i).*" + dest + ".*");
    }

    public boolean processRegionMatches(String localSrc, String localDest) {
        final char firstLo = Character.toLowerCase(localDest.charAt(0));
        final char firstUp = Character.toUpperCase(localDest.charAt(0));

        for (int i = localSrc.length() - localDest.length(); i >= 0; i--) {
            final char ch = localSrc.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (localSrc.regionMatches(true, i, localDest, 0, localDest.length()))
                return true;
        }

        return false;        
    }
    
    // String regionMatches()
    @Benchmark
    public boolean regionMatches() {
        return processRegionMatches(src, dest);
    }

    // Pattern CASE_INSENSITIVE with regexp
    @Benchmark
    public boolean patternCaseInsensitiveRegexp() {
        return pattern.matcher(src)
            .find();
    }

    // Apache Commons StringUtils containsIgnoreCase
    @Benchmark
    public boolean apacheCommonsStringUtils() {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(src, dest);
    }

}
