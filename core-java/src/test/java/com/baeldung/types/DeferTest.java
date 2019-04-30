package com.baeldung.types;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeferTest {
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello!");
            }
        };

        Defer.defer(runnable);
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
    void givenAction_whenDiffer_thenNoReturn() {
        Action action = () -> System.out.println("Hello!");

        Defer.defer(action);
    }
}