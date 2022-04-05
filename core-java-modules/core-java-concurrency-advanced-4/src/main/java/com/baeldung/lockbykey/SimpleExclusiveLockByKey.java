package com.baeldung.lockbykey;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleExclusiveLockByKey {

    private static Set<String> usedKeys= ConcurrentHashMap.newKeySet();
    
    public boolean tryLock(String key) {
        return usedKeys.add(key);
    }
    
    public void unlock(String key) {
        usedKeys.remove(key);
    }

}
