package org.baeldung.spring43.scopeannotations;

import java.util.concurrent.atomic.AtomicInteger;

public class InstanceCountingService {

    private static final AtomicInteger instanceCount = new AtomicInteger(0);

    private final int instanceNumber = instanceCount.incrementAndGet();

    public int getInstanceNumber() {
        return instanceNumber;
    }

}
