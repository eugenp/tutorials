package com.baeldung.streams.partitioning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Iterables;

public class PartitionStream {

    public static <T> Stream<List<T>> partitionList(List<T> source, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException(String.format("Expected the batchSize to be greater than ZERO, actual value was: %s", batchSize));
        }
        if (source.isEmpty()) {
            return Stream.empty();
        }
        int nrOfFullBatches = (source.size() - 1) / batchSize;
        return IntStream.rangeClosed(0, nrOfFullBatches)
          .mapToObj(batch -> {
              int startIndex = batch * batchSize;
              int endIndex = (batch == nrOfFullBatches) ? source.size() : (batch + 1) * batchSize;
              return source.subList(startIndex, endIndex);
          });
    }

    public static <T> Iterable<List<T>> partitionUsingGuava(Stream<T> source, int batchSize) {
        return Iterables.partition(source::iterator, batchSize);
    }

    public static <T> List<List<T>> partitionStream(Stream<T> source, int batchSize) {
        return source.collect(partitionBySize(batchSize, Collectors.toList()));
    }

    public static <T, A, R> Collector<T, ?, R> partitionBySize(int batchSize, Collector<List<T>, A, R> downstream) {
        Supplier<Accumulator<T, A>> supplier = () -> new Accumulator<>(
          batchSize,
          downstream.supplier().get(),
          downstream.accumulator()::accept
        );

        BiConsumer<Accumulator<T, A>, T> accumulator = (acc, value) -> acc.add(value);

        BinaryOperator<Accumulator<T, A>> combiner = (acc1, acc2) -> acc1.combine(acc2, downstream.combiner());

        Function<Accumulator<T, A>, R> finisher = acc -> {
            if (!acc.values.isEmpty()) {
                downstream.accumulator().accept(acc.downstreamAccumulator, acc.values);
            }
            return downstream.finisher().apply(acc.downstreamAccumulator);
        };

        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }

    static class Accumulator<T, A> {
        private final List<T> values = new ArrayList<>();
        private final int batchSize;
        private A downstreamAccumulator;
        private final BiConsumer<A, List<T>> batchFullListener;

        Accumulator(int batchSize, A accumulator, BiConsumer<A, List<T>> onBatchFull) {
            this.batchSize = batchSize;
            this.downstreamAccumulator = accumulator;
            this.batchFullListener = onBatchFull;
        }

        void add(T value) {
            values.add(value);
            if (values.size() == batchSize) {
                batchFullListener.accept(downstreamAccumulator, new ArrayList<>(values));
                values.clear();
            }
        }

        Accumulator<T, A> combine(Accumulator<T, A> other, BinaryOperator<A> accumulatorCombiner) {
            this.downstreamAccumulator = accumulatorCombiner.apply(downstreamAccumulator, other.downstreamAccumulator);
            other.values.forEach(this::add);
            return this;
        }
    }

}
