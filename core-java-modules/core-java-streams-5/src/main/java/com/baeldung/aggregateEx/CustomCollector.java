package com.baeldung.aggregateEx;

import com.baeldung.aggregateEx.entity.ExceptionAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

public class CustomCollector<T, R> {
    private final List<R> results = new ArrayList<>();
    private final List<Throwable> exceptions = new ArrayList<>();

    private final ExceptionAggregator exceptionAggregator = new ExceptionAggregator("Exceptions occurred");

    public static <T, R> Collector<T, ?, CustomCollector<T, R>> of(Function<T, R> mapper) {
        return Collector.of(
                CustomCollector::new,
                (collector, item) -> {
                    try {
                        R result = mapper.apply(item);
                        collector.results.add(result);
                    } catch (Exception e) {
                        collector.exceptions.add(e);
                        collector.exceptionAggregator.addException(e);
                    }
                },
                (left, right) -> {
                    left.results.addAll(right.results);
                    left.exceptions.addAll(right.exceptions);
                    left.exceptionAggregator.addExceptions(right.exceptions);
                    return left;
                }
        );
    }

    public List<R> getResults() {
        return results;
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }

    public ExceptionAggregator getExceptionAggregator() {
        return exceptionAggregator;
    }
}
