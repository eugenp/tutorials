package com.baeldung.infrastructure.util;

import java.util.concurrent.atomic.AtomicInteger;

public class RepositoryUtil {

    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static int getPrimaryKey() {
        return atomicInt.incrementAndGet();
    }
}