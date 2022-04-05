package com.baeldung.reflection.voidtype;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeferUnitTest {
    @Test
    void givenVoidCallable_whenDiffer_thenReturnNull() throws Exception {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() {
                System.out.println("Hello!");
                return null;
            }
        };

        assertThat(Defer.defer(callable)).isNull();
    }

    @Test
    void givenVoidRunnable_whenDiffer_thenNoReturn() {
        AtomicBoolean run = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello!");
                run.set(true);
            }
        };

        Defer.defer(runnable);

        assertTrue(run.get());
    }

    @Test
    void givenVoidFunction_whenDiffer_thenReturnNull() {
        Function<String, Void> function = s -> {
            System.out.println("Hello " + s + "!");
            return null;
        };

        assertThat(Defer.defer(function, "World")).isNull();
    }

    @Test
    void givenVoidConsumer_whenDiffer_thenReturnNull() {
        AtomicBoolean run = new AtomicBoolean(false);

        Consumer<String> function = s -> {
            System.out.println("Hello " + s + "!");
            run.set(true);
        };

        Defer.defer(function, "World");

        assertTrue(run.get());
    }

    @Test
    void givenAction_whenDiffer_thenNoReturn() {
        AtomicBoolean run = new AtomicBoolean(false);

        Action action = () -> {
            System.out.println("Hello!");
            run.set(true);
        };

        Defer.defer(action);

        assertTrue(run.get());
    }
}