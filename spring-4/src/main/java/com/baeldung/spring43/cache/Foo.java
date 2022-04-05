package com.baeldung.spring43.cache;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foo {

    private static final Logger log = LoggerFactory.getLogger(Foo.class);

    private static final AtomicInteger instanceCount = new AtomicInteger(0);

    private final int instanceNum;

    public Foo() {
        instanceNum = instanceCount.incrementAndGet();
    }

    public static int getInstanceCount() {
        return instanceCount.get();
    }

    public void printInstanceNumber() {
        log.info("Foo instance number: {}", instanceNum);
    }

}
