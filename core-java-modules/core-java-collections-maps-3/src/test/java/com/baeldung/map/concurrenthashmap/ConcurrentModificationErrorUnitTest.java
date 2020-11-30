package com.baeldung.map.concurrenthashmap;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

public class ConcurrentModificationErrorUnitTest {

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAndAddOnHashMap_thenConcurrentModificationError() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "baeldung");
        map.put(2, "HashMap");
        Map<Integer, String> synchronizedMap = Collections.synchronizedMap(map);
        Iterator<Entry<Integer, String>> iterator = synchronizedMap.entrySet().iterator();
        while (iterator.hasNext()) {
            synchronizedMap.put(3, "Modification");
            iterator.next();
        }
    }

    public void whenRemoveAndAddOnConcurrentHashMap_thenNoError() {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "baeldung");
        map.put(2, "HashMap");
        Iterator<Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            map.put(3, "Modification");
            iterator.next();
        }

        Assert.assertEquals(3, map.size());
    }
}