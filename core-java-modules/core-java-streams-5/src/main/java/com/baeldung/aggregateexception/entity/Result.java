package com.baeldung.aggregateexception.entity;

import java.util.Optional;

public class Result<R, E extends Throwable> {
    private Optional<R> result;
    private Optional<E> exception;

    public Result(R result) {
        this.result = Optional.of(result);
        this.exception = Optional.empty();
    }

    public Result(E exception) {
        this.exception = Optional.of(exception);
        this.result = Optional.empty();
    }

    public Optional<R> getResult() {
        return result;
    }

    public Optional<E> getException() {
        return exception;
    }
}
