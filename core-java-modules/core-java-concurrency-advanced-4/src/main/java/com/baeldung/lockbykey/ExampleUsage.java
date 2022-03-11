package com.baeldung.lockbykey;

/**
 * This class shows examples of how you should use the lock
 *
 */
public class ExampleUsage {
    
    void doWithSimpleExclusiveLock(String key) {
        SimpleExclusiveLockByKey simpleExclusiveLockByKey = new SimpleExclusiveLockByKey();
        if (simpleExclusiveLockByKey.tryLock(key)) {
            try {
                // do stuff
            } finally {
                // it is very important to unlock in the finally block to avoid locking keys forever
                simpleExclusiveLockByKey.unlock(key);
            }
        }
    }
    
    // A concrete example can be found in the unit tests
    void doWithLock(String key) {
        LockByKey lockByKey = new LockByKey();
        lockByKey.lock(key);
        try {
            // do stuff
        } finally {
            lockByKey.unlock(key);
        }
    }
    
    // It works exactly the same as with locks
    void doWithSemaphore(String key) {
        SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
        lockByKey.lock(key);
        try {
            // do stuff
        } finally {
            lockByKey.unlock(key);
        }
    }

}
