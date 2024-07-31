package com.baeldung.algorithms.weightedaverage;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightedAverageUnitTest {

    private List<Values> values = Arrays.asList(
            new Values(1, 10),
            new Values(3, 20),
            new Values(5, 30),
            new Values(7, 50),
            new Values(9, 40)
    );

    private Double expected = 6.2;

    @Test
    void twoPass() {
        double top = values.stream()
                .mapToDouble(v -> v.value * v.weight)
                .sum();
        double bottom = values.stream()
                .mapToDouble(v -> v.weight)
                .sum();

        double result = top / bottom;
        assertEquals(expected, result);
    }

    @Test
    void onePass() {
        double top = 0;
        double bottom = 0;

        for (Values v : values) {
            top += (v.value * v.weight);
            bottom += v.weight;
        }

        double result = top / bottom;
        assertEquals(expected, result);
    }

    @Test
    void expanding() {
        double result = values.stream()
                .flatMap(v -> Collections.nCopies(v.weight, v.value).stream())
                .mapToInt(v -> v)
                .average()
                .getAsDouble();
        assertEquals(expected, result);
    }

    @Test
    void reduce() {
        class WeightedAverage {
            final double top;
            final double bottom;

            public WeightedAverage(double top, double bottom) {
                this.top = top;
                this.bottom = bottom;
            }

            double average() {
                return top / bottom;
            }
        }

        double result = values.stream()
                .reduce(new WeightedAverage(0, 0),
                        (acc, next) -> new WeightedAverage(
                                acc.top + (next.value * next.weight),
                                acc.bottom + next.weight),
                        (left, right) -> new WeightedAverage(
                                left.top + right.top,
                                left.bottom + right.bottom))
                .average();
        assertEquals(expected, result);
    }

    @Test
    void customCollector() {
        class WeightedAverage implements Collector<Values, WeightedAverage.RunningTotals, Double> {
            class RunningTotals {
                double top;
                double bottom;

                public RunningTotals() {
                    this.top = 0;
                    this.bottom = 0;
                }
            }

            @Override
            public Supplier<RunningTotals> supplier() {
                return RunningTotals::new;
            }

            @Override
            public BiConsumer<RunningTotals, Values> accumulator() {
                return (current, next) -> {
                    current.top += (next.value * next.weight);
                    current.bottom += next.weight;
                };
            }

            @Override
            public BinaryOperator<RunningTotals> combiner() {
                return (left, right) -> {
                    left.top += right.top;
                    left.bottom += right.bottom;

                    return left;
                };
            }

            @Override
            public Function<RunningTotals, Double> finisher() {
                return rt -> rt.top / rt.bottom;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.singleton(Characteristics.UNORDERED);
            }
        }

        double result = values.stream()
                .collect(new WeightedAverage());
        assertEquals(expected, result);
    }

    private static class Values {
        int value;
        int weight;

        public Values(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
