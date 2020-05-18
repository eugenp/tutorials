package com.baeldung.architecture.hexagonal.callers;

public interface CallerAdapter<T> {
    void adaptAndCallComponent(T t);
}
