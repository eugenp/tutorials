package com.baeldung.map.concurrenthashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

public class NullAllowInMapTest {
    
    @Test
    public void allowOnlyNull_In_SynchronizedMap() {
        Map<String, Integer> map = Collections
                .synchronizedMap(new HashMap<String, Integer>());
        map.put(null, 1);
        Assert.assertTrue(map.get(null).equals(1));
    }
    
    @Test(expected = NullPointerException.class)
    public void allowOnlyNull_In_ConcurrentHasMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put(null, 1);
    }
    
}
