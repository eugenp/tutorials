package com.baeldung.executeonce;

final class SynchronizedInitializer {

    private static volatile boolean isInitialized = false;
    int callCount = 0;

    synchronized void initialize() {
        if (!isInitialized) {
            initializationLogic();
            isInitialized = true;
        }
    }

    private void initializationLogic() {
        callCount++;
    }
}