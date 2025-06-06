package com.baeldung.pipeline.immutable;

public interface Pipe<IN, OUT> {
    OUT process(IN input);
}
