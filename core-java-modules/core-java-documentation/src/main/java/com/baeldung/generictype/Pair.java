package com.baeldung.generictype;

/**
 * @param <T> The type of the first value in the {@code Pair<T,S>}.
 * @param <S> The type of the second value in the {@code Pair<T,S>}.
 */

public class Pair<T, S> {
    public T first;
    public S second;

    /**
     * Constructs a new Pair object with the specified values.
     *
     * @param a The first value.
     * @param b The second value.
     */

    public Pair(T a, S b) {
        first = a;
        second = b;
    }
}
