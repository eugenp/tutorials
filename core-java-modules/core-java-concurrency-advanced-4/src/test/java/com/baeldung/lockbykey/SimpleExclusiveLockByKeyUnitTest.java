package com.baeldung.lockbykey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleExclusiveLockByKeyUnitTest {
    
    @BeforeEach
    void cleanUpLocks() throws Exception {
        Field field = SimpleExclusiveLockByKey.class.getDeclaredField("usedKeys");
        field.setAccessible(true);
        field.set(null, ConcurrentHashMap.newKeySet());
    }
    
    @Test
    void givenNoLockedKey_WhenTryLock_ThenSuccess() {
        SimpleExclusiveLockByKey lockByKey = new SimpleExclusiveLockByKey();
        assertTrue(lockByKey.tryLock("key"));
    }
    
    @Test
    void givenLockedKey_WhenTryLock_ThenFailure() {
        String key = "key";
        SimpleExclusiveLockByKey lockByKey = new SimpleExclusiveLockByKey();
        lockByKey.tryLock(key);
        assertFalse(lockByKey.tryLock(key));
    }
    
    @Test
    void givenAnotherKeyLocked_WhenTryLock_ThenSuccess() {
        SimpleExclusiveLockByKey lockByKey = new SimpleExclusiveLockByKey();
        lockByKey.tryLock("other");
        assertTrue(lockByKey.tryLock("key"));
    }
    
    @Test
    void givenUnlockedKey_WhenTryLock_ThenSuccess() {
        String key = "key";
        SimpleExclusiveLockByKey lockByKey = new SimpleExclusiveLockByKey();
        lockByKey.tryLock(key);
        lockByKey.unlock(key);
        assertTrue(lockByKey.tryLock(key));
    }

}
