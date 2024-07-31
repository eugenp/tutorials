package com.baeldung.reflection.voidtype;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class Defer {
    public static <V> V defer(Callable<V> callable) throws Exception {
        return callable.call();
    }

    public static void defer(Runnable runnable) {
        runnable.run();
    }

    public static <T, R> R defer(Function<T, R> function, T arg) {
        return function.apply(arg);
    }

    public static <T> void defer(Consumer<T> consumer, T arg) {
        consumer.accept(arg);
    }

    public static void defer(Action action) {
        action.execute();
    }
}
