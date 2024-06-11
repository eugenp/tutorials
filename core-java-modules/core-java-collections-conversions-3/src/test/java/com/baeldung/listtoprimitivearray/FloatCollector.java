package com.baeldung.listtoprimitivearray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FloatCollector implements Collector<Float, float[], float[]> {

    private final int size;
    private int index = 0;
    public FloatCollector(int size) {
        this.size = size;
    }

    @Override
    public Supplier<float[]> supplier() {
        return () -> new float[size];
    }

    @Override
    public BiConsumer<float[], Float> accumulator() {
        return (array, number) -> {
            array[index] = number;
            index++;
        };
    }

    @Override
    public BinaryOperator<float[]> combiner() {
        return null;
    }

    @Override
    public Function<float[], float[]> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
