package com.baeldung.nulls;

import java.util.Optional;

public class UsingOptional {

    public Optional<Object> process() {
        if (isProcessed())
            return Optional.of("dummy");
        else
            return Optional.empty();
    }

    public void caller() {
        Optional<Object> result = process();
        result.ifPresent(p -> System.out.println(p));
        result.orElseThrow(() -> new IllegalArgumentException());
    }

    private boolean isProcessed() {
        return false;
    }
}
