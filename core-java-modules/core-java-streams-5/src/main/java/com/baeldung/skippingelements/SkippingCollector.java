package com.baeldung.skippingelements;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

class SkippingCollector {

    private static final BinaryOperator<SkippingCollector> IGNORE_COMBINE = (a, b) -> a;
    private final int skip;
    private final List<String> list = new ArrayList<>();
    private int currentIndex = 0;

    private SkippingCollector(int skip) {
        this.skip = skip;
    }

    private void accept(String item) {
        final int index = ++currentIndex % skip;
        if (index == 0) {
            list.add(item);
        }
    }

    private List<String> getResult() {
        return list;
    }

    public static Collector<String, SkippingCollector, List<String>> collector(int skip) {
        return Collector.of(() -> new SkippingCollector(skip),
            SkippingCollector::accept,
            IGNORE_COMBINE,
            SkippingCollector::getResult);
    }
}
