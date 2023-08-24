package com.baeldung.aggregateEx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

public class ExceptionCollector<T, R> {
    private final List<R> results = new ArrayList<>();
    private final List<Exception> exceptions = new ArrayList<>();

    private ExceptionCollector() {
    }

    public static <T, R> Collector<T, ?, ExceptionCollector<T, R>> of(Function<T, R> mapper) {
        return Collector.of(
                ExceptionCollector::new,
                (collector, item) -> {
                    try {
                        R result = mapper.apply(item);
                        collector.results.add(result);
                    } catch (Exception e) {
                        collector.exceptions.add(e);
                    }
                },
                (left, right) -> {
                    left.results.addAll(right.results);
                    left.exceptions.addAll(right.exceptions);
                    return left;
                }
        );
    }

    public List<R> getResults() {
        return results;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
