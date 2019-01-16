package com.baeldung.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class StreamReduce {

    public static String reduceWithAccumulator(String... input) {
        Optional<String> result = Arrays.stream(input)
            .reduce((s1, s2) -> s1 + s2);
        return result.orElse(null);
    }
    
    public static void simpleReduce() {
        Integer[] input = { 10, 20, 45, 6 };
        Integer sum = 0;
        sum = Arrays.stream(input).reduce(Integer::sum).orElse(null);
        System.out.println("Sum of all elements:" + sum);
    }

    public static String reduceWithAccumulatorAndIdentity(String... input) {
        String result = Arrays.stream(input)
            .parallel()
            .reduce("", ((s1, s2) -> s1 + s2));
        return result;
    }

    public static Integer reduceWithAccumulatorAndIdentity(Integer... input) {
        Integer result = Arrays.stream(input)
            .reduce(0, ((i1, i2) -> i1 + i2));
        return result;
    }

    public static int reduceWithAccumulatorIdentityAndCombiner(String... input) {
        int result = Arrays.stream(input)
            .reduce(0, (s1, s2) -> {
                System.out.println("accumulator called with:" + s1 + " & " + s2);
                return s1 + s2.length();
            }, ((s1, s2) -> {
                System.out.println("combiner called with:" + s1 + " & " + s2);
                return s1 + s2;
            }));
        System.out.println("result is" + result);
        return result;
    }

    public static String reduceWithAccumulatorReturnLongestString(String... input) {
        Optional<String> result = Arrays.stream(input)
            .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
        return result.orElse(null);
    }
    
    public static String findLongestStringWithoutStreamReduction(String...input) {
        int index = 0, length = 0;
        if(input.length != 0) {
            for(int i=0; i<input.length; i++) {
                if(input[i].length() > length) {
                    {
                        index=i;
                        length = input[i].length();
                    }
                }
            }
            return input[index];
        }
        return null;
    }

    public static void parallelReduction(String... input) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();

        OptionalInt resultSeq = IntStream.range(1, 50000)
            .reduce((s1, s2) -> s1 + s2);

        endTime = System.currentTimeMillis();
        System.out.println("Total time in non parallel:" + (endTime - startTime) + " result-seq:" + resultSeq.getAsInt());

        // parallel streams
        startTime = System.currentTimeMillis();
        OptionalInt resultParallel = IntStream.range(1, 50000)
            .reduce((s1, s2) -> s1 + s2);
        endTime = System.currentTimeMillis();
        System.out.println("Total time in  parallel:" + (endTime - startTime) + " result-parallel:" + resultParallel.getAsInt());
    }

    public static <T> long predefinedReductionCount(Collection<T> input) {
        return input.stream()
            .count();
    }

    public static int predefinedReductionSum(int[] input) {
        return IntStream.of(input)
            .sum();
    }

    public static int predefinedReductionMin(int[] input) {
        return IntStream.of(input)
            .min()
            .orElse(Integer.MIN_VALUE);
    }

    public static int predefinedReductionMax(int[] input) {
        return IntStream.of(input)
            .max()
            .orElse(Integer.MAX_VALUE);
    }

    public static double predefinedReductionAverage(int[] input) {
        return IntStream.of(input)
            .average()
            .orElse(0);
    }

    public static IntSummaryStatistics predefinedReductionSummaryStats(int[] input) {
        return IntStream.of(input)
            .summaryStatistics();
    }

}
