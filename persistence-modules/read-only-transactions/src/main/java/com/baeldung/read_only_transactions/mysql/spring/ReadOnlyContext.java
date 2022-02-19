package com.baeldung.read_only_transactions.mysql.spring;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadOnlyContext {
    private static final ThreadLocal<AtomicInteger> READ_ONLY_LEVEL = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    static int getValue() { return READ_ONLY_LEVEL.get().get(); }

    public static boolean isReadOnly() {
        return READ_ONLY_LEVEL.get().get() > 0;
    }

    public static void enter() {
        READ_ONLY_LEVEL.get().incrementAndGet();
    }

    public static void exit() {
        READ_ONLY_LEVEL.get().decrementAndGet();
    }
}
