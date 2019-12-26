package com.baeldung.reflection.voidtype;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class MyOwnDeferUnitTest {
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