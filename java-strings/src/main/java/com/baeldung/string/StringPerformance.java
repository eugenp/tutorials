package com.baeldung.string;

import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 10000, iterations = 10)
@Warmup(batchSize = 10000, iterations = 10)
public class StringPerformance extends StringPerformanceHints {

    @Benchmark
    public String benchmarkStringDynamicConcat() {
        return dynamicConcat();
    }

    @Benchmark
    public StringBuilder  benchmarkStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.append(baeldung);
        return stringBuilder;
    }

    @Benchmark
    public StringBuffer benchmarkStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer(result);
        stringBuffer.append(baeldung);
        return stringBuffer;
    }

    @Benchmark
    public String benchmarkStringConstructor() {
        return stringConstructor();
    }

    @Benchmark
    public String benchmarkStringLiteral() {
        return stringLiteral();
    }

    @Benchmark
    public String benchmarkStringFormat_s() {
        return stringFormat_s();
    }

    @Benchmark
    public String benchmarkStringConcat() {
        return stringConcat();
    }

    @Benchmark
    public String benchmarkStringIntern() {
        return stringIntern();
    }

    @Benchmark
    public String benchmarkStringReplace() {
        return longString.replace("average", " average !!!");
    }

    @Benchmark
    public String benchmarkStringUtilsReplace() {
        return StringUtils.replace(longString, "average", " average !!!");
    }

    @Benchmark
    public List<String> benchmarkGuavaSplitter() {
        return guavaSplitter();
    }

    @Benchmark
    public String [] benchmarkStringSplit() {
        return stringSplit();
    }

    @Benchmark
    public String [] benchmarkStringSplitPattern() {
        return stringSplitPattern();
    }

    @Benchmark
    public List benchmarkStringTokenizer() {
        return stringTokenizer();
    }

    @Benchmark
    public List benchmarkStringIndexOf() {
        return stringIndexOf();
    }


    @Benchmark
    public String benchmarkIntegerToString() {
        return stringIntegerToString();
    }

    @Benchmark
    public String benchmarkStringValueOf() {
        return stringValueOf();
    }


    @Benchmark
    public String benchmarkStringConvertPlus() {
        return stringConvertPlus();
    }

        @Benchmark
    public String benchmarkStringFormat_d() {
        return stringFormat_d();
    }

    @Benchmark
    public boolean benchmarkStringEquals() {
        return stringEquals();
    }


    @Benchmark
    public boolean benchmarkStringEqualsIgnoreCase() {
        return stringEqualsIgnoreCase();
    }

    @Benchmark
    public boolean benchmarkStringMatches() {
        return stringIsMatch();
    }

    @Benchmark
    public boolean benchmarkPrecompiledMatches() {
        return precompiledMatches();
    }

    @Benchmark
    public int benchmarkStringCompareTo() {
        return stringCompareTo();
    }

    @Benchmark
    public boolean benchmarkStringIsEmpty() {
        return stringIsEmpty();
    }

    @Benchmark
    public boolean benchmarkStringLengthZero() {
        return stringLengthZero();
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
