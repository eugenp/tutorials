package com.baeldung.patterns.hexagonal_quick.util;

public interface Converter<S, T> {
    T convert(S subject);
}
