package com.baeldung.lockbykey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class LockByKeyUnitTest {
    
    @Test
    void givenNoLockedKey_WhenLock_ThenSuccess() throws InterruptedException {
        AtomicBoolean threadWasExecuted = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            String key = "key";
            LockByKey lockByKey = new LockByKey();
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
    void givenLockedKey_WhenLock_ThenFailure() throws InterruptedException {
        String key = "key";
        LockByKey lockByKey = new LockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            LockByKey otherLockByKey = new LockByKey();
            otherLockByKey.lock(key);
            try {
                anotherThreadWasExecuted.set(true);
            } finally {
                otherLockByKey.unlock(key);
            }
        });
        try {
            threadLockingOnAnotherKey.start();
            Thread.sleep(100);
        } finally {
            assertFalse(anotherThreadWasExecuted.get());
            lockByKey.unlock(key);
        }
    }
    
    @Test
    void givenAnotherKeyLocked_WhenLock_ThenSuccess() throws InterruptedException {
        String key = "key";
        LockByKey lockByKey = new LockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            String anotherKey = "anotherKey";
            LockByKey otherLockByKey = new LockByKey();
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
        LockByKey lockByKey = new LockByKey();
        lockByKey.lock(key);
        AtomicBoolean anotherThreadWasExecuted = new AtomicBoolean(false);
        Thread threadLockingOnAnotherKey = new Thread(() -> {
            LockByKey otherLockByKey = new LockByKey();
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
