package com.baeldung.concurrent.synchronize;

public class SynchronisedBlocks {
    public void performSynchrinisedTask() {
        synchronized (this) {
            // Perform thread safe work
        }
    }

    public static void performStaticSyncTask() {
        synchronized (SynchronisedBlocks.class) {
            // Perform some critical work
        }
    }
}
