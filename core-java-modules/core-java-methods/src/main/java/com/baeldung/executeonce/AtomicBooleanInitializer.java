package com.baeldung.executeonce;

import java.util.concurrent.atomic.AtomicBoolean;

final class AtomicBooleanInitializer {

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);
    int callCount = 0;

    void initialize() {
        if (isInitialized.compareAndSet(false, true)) {
            initializationLogic();
        }
    }

    private void initializationLogic() {
        callCount++;
    }
}