package com.baeldung.aggregateEx.entity;

import java.util.Optional;

public class Result<R> {
    private Optional<R> result;
    private Optional<Exception> exception;

    public Result(R result) {
        this.result = Optional.of(result);
        this.exception = Optional.empty();
    }

    public Result(Exception exception) {
        this.exception = Optional.of(exception);
        this.result = Optional.empty();
    }

    public Optional<R> getResult() {
        return result;
    }

    public Optional<Exception> getException() {
        return exception;
    }

}
