package com.baeldung.types;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class MyOwnDeferTest {
    @Test
    void defer() throws Exception {
        AtomicBoolean run = new AtomicBoolean(false);
        Runnable runnable = () -> {
            System.out.println("Hello!");
            run.set(true);
        };

        MyOwnDefer.defer(runnable);

        assertTrue(run.get());
    }
}