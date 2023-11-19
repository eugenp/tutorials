package com.baeldung.functional.functors;

import java.util.function.Function;

public class Functor<T> {
    private final T value;

    public Functor(T value) {
        this.value = value;
    }

    public <R> Functor<R> map(Function<T, R> mapper) {
        return new Functor<>(mapper.apply(value));
    }

    boolean eq(T other) {
        return value.equals(other);
    }

    public T getValue() {
        return value;
    }
}