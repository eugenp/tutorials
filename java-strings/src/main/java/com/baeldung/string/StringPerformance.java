package com.baeldung.string;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10)
public class StringPerformance {

    @State(Scope.Thread)
    public static class MyState {
        int iterations = 100000;

        String sample = "baeldung";
        String longString = "Hello baeldung, I am a bit longer than other Strings";
        String result = "";
    }

    @Benchmark
    public void benchmarkStringDynamicConcat(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            state.result += state.sample;
        }
    }

    @Benchmark
    public StringBuilder  benchmarkStringBuilder(StringPerformance.MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.result);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.sample);
        }

        return stringBuilder;
    }

    @Benchmark
    public StringBuffer benchmarkStringBuffer(StringPerformance.MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.result);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.sample);
        }

        return stringBuffer;
    }

    @Benchmark
    public void benchmarkStringConstructor(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String newString = new String(state.sample);
        }
    }

    @Benchmark
    public void benchmarkStringLiteral(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String newString = state.sample;
        }
    }

    @Benchmark
    public void benchmarkStringFormat_s(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String s = String.format("hello %s, nice to meet you", state.sample);
        }
    }

    @Benchmark
    public void benchmarkStringFormat_d(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String s = String.format("hello %d, nice to meet you", i);
        }
    }


    @Benchmark
    public void benchmarkStringConcat(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            state.result.concat(state.sample);
        }
    }

    @Benchmark
    public void benchmarkStringIntern(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String number = Integer.toString( i );
            String interned = number.intern();
        }
    }

    @Benchmark
    public void benchmarkStringReplace(StringPerformance.MyState state) {
        String replaced = state.longString.replace("average", " average !!!");
    }

    @Benchmark
    public void benchmarkStringUtilsReplace(StringPerformance.MyState state) {
        String replaced = StringUtils.replace(state.longString, "average", " average !!!");
    }


    @Benchmark
    public void benchmarkGuavaSplitter(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            List<String> resultList = Splitter.on(" ").trimResults()
                    .omitEmptyStrings()
                    .splitToList(state.longString);
        }
    }

    @Benchmark
    public void benchmarkStringSplit(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String [] list = state.longString.split(" ");
        }
    }

    @Benchmark
    public void benchmarkStringSplitPattern(StringPerformance.MyState state) {
        Pattern spacePattern = Pattern.compile(" ");
        for (int i = 0; i < state.iterations; i++) {
            String [] list = spacePattern.split(state.longString, 0);
        }
    }

    @Benchmark
    public void benchmarkStringTokenizer(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            StringTokenizer st = new StringTokenizer(state.longString);
            List<String> list = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
        }
    }

    @Benchmark
    public void benchmarkStringIndexOf(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            List<String> list = new ArrayList<String>();
            int pos = 0, end;
            while ((end = state.longString.indexOf(' ', pos)) >= 0) {
                list.add(state.longString.substring(pos, end));
                pos = end + 1;
            }
        }
    }

    @Benchmark
    public void benchmarkIntegerToString(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String number = Integer.toString(i);
        }
    }

    @Benchmark
    public void benchmarkStringValueOf(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String number = String.valueOf(i);
        }
    }

    @Benchmark
    public void benchmarkStringConvertPlus(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            String number = i + "";
        }
    }

    @Benchmark
    public void benchmarkStringEquals(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            boolean isEqual = state.longString.equals(state.sample);
        }
    }

    @Benchmark
    public void benchmarkStringEqualsIgnoreCase(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            boolean isEqual = state.longString.equalsIgnoreCase(state.sample);
        }
    }

    @Benchmark
    public void benchmarkStringMatches(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            boolean ismatch = state.longString.matches(state.sample);
        }
    }

    @Benchmark
    public void benchmarkPrecompiledMatches(StringPerformance.MyState state) {
        Pattern pattern = Pattern.compile(state.longString);
        for (int i = 0; i < state.iterations; i++) {
            boolean ismatch = pattern.matcher(state.sample).matches();
        }
    }

    @Benchmark
    public void benchmarkStringCompareTo(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            int result = state.longString.compareTo(state.sample);
        }
    }


    @Benchmark
    public void benchmarkStringIsEmpty(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            boolean result = state.longString.isEmpty();
        }
    }

    @Benchmark
    public void benchmarkStringLengthZero(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            boolean result = state.longString.length() == 0;
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
