package com.baeldung.examples.guice;

import org.springframework.lang.Nullable;

import com.google.inject.Inject;

public class FooGenerator {
    @Inject
    public FooGenerator(@Nullable Foo foo) {
    }
}