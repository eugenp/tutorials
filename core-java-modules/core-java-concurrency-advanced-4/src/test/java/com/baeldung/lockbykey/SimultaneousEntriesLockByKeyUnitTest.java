package com.baeldung.lockbykey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class SimultaneousEntriesLockByKeyUnitTest {

    @Test
    void givenNoKeyUsed_WhenLock_ThenSuccess() throws InterruptedException {
        AtomicBoolean threadWasExecuted = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            String key = "key";
            SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
            lockByKey.lock(key);
            try {
               threadWasExecuted.set(true);
            } finally {
                lockByKey.unlock(key);
            }
        });
        try {
            thread.start();
            Thread.sleep(100);
        } finally {
            assertTrue(threadWasExecuted.get());
        }
    }
    
    @Test
    void givenKeyLockedWithRemainingPermits_WhenLock_ThenSuccess() throws InterruptedException {
        String key = "key";
        SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            SimultaneousEntriesLockByKey otherLockByKeyWithSemaphore = new SimultaneousEntriesLockByKey();
            otherLockByKeyWithSemaphore.lock(key);
            try {
                anotherThreadWasExecuted.set(true);
            } finally {
                otherLockByKeyWithSemaphore.unlock(key);
            }
        });
        try {
            threadLockingOnAnotherKey.start();
            Thread.sleep(100);
        } finally {
            assertTrue(anotherThreadWasExecuted.get());
            lockByKey.unlock(key);
        }
    }
    
    @Test
    void givenKeyLockedWithNoRemainingPermits_WhenLock_ThenFailure() throws InterruptedException {
        String key = "key";
        SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey1 = new Thread(() -> {
            SimultaneousEntriesLockByKey otherLockByKeyWithSemaphore = new SimultaneousEntriesLockByKey();
            otherLockByKeyWithSemaphore.lock(key);
            try {
                Thread.sleep(200); // make sure this thread will release the lock after the assertion
            } catch (InterruptedException e) {
                
            } finally {
                otherLockByKeyWithSemaphore.unlock(key);
            }
        });
        Thread threadLockingOnAnotherKey2 = new Thread(() -> {
            SimultaneousEntriesLockByKey otherLockByKey = new SimultaneousEntriesLockByKey();
            try {
                Thread.sleep(50); // make sure thread1 will acquire the key first
            } catch (InterruptedException e) {
            }
            otherLockByKey.lock(key);
            try {
                anotherThreadWasExecuted.set(true);
            } finally {
                otherLockByKey.unlock(key);
            }
        });
        try {
            threadLockingOnAnotherKey1.start();
            threadLockingOnAnotherKey2.start();
            Thread.sleep(100);
        } finally {
            assertFalse(anotherThreadWasExecuted.get());
            lockByKey.unlock(key);
        }
    }
    
    @Test
    void givenAnotherKeyLocked_WhenLock_ThenSuccess() throws InterruptedException {
        String key = "key";
        SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            String anotherKey = "anotherKey";
            SimultaneousEntriesLockByKey otherLockByKey = new SimultaneousEntriesLockByKey();
            otherLockByKey.lock(anotherKey);
            try {
                anotherThreadWasExecuted.set(true);
            } finally {
                otherLockByKey.unlock(anotherKey);
            }
        });
        try {
            threadLockingOnAnotherKey.start();
            Thread.sleep(100);
        } finally {
            assertTrue(anotherThreadWasExecuted.get());
            lockByKey.unlock(key);
        }
    }
    
    @Test
    void givenUnlockedKey_WhenLock_ThenSuccess() throws InterruptedException {
        String key = "key";
        SimultaneousEntriesLockByKey lockByKey = new SimultaneousEntriesLockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            SimultaneousEntriesLockByKey otherLockByKey = new SimultaneousEntriesLockByKey();
            otherLockByKey.lock(key);
            try {
                anotherThreadWasExecuted.set(true);
            } finally {
                otherLockByKey.unlock(key);
            }
        });
        try {
            lockByKey.unlock(key);
            threadLockingOnAnotherKey.start();
            Thread.sleep(100);
        } finally {
            assertTrue(anotherThreadWasExecuted.get());
        }
    }
    
}
