package com.baeldung.java.concurrentmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertNull;

public class ConcurrentMapNullKeyValueManualTest {

    private ConcurrentMap<String, Object> concurrentMap;

    @Before
    public void setup() {
        concurrentMap = new ConcurrentHashMap<>();
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenGetWithNullKey_thenThrowsNPE() {
        concurrentMap.get(null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenGetOrDefaultWithNullKey_thenThrowsNPE() {
        concurrentMap.getOrDefault(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenPutWithNullKey_thenThrowsNPE() {
        concurrentMap.put(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenPutNullValue_thenThrowsNPE() {
        concurrentMap.put("test", null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMapAndKeyAbsent_whenPutWithNullKey_thenThrowsNPE() {
        concurrentMap.putIfAbsent(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMapAndMapWithNullKey_whenPutNullKeyMap_thenThrowsNPE() {
        Map<String, Object> nullKeyMap = new HashMap<>();
        nullKeyMap.put(null, new Object());
        concurrentMap.putAll(nullKeyMap);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMapAndMapWithNullValue_whenPutNullValueMap_thenThrowsNPE() {
        Map<String, Object> nullValueMap = new HashMap<>();
        nullValueMap.put("test", null);
        concurrentMap.putAll(nullValueMap);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceNullKeyWithValues_thenThrowsNPE() {
        concurrentMap.replace(null, new Object(), new Object());
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceWithNullNewValue_thenThrowsNPE() {
        Object o = new Object();
        concurrentMap.replace("test", o, null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceOldNullValue_thenThrowsNPE() {
        Object o = new Object();
        concurrentMap.replace("test", null, o);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceWithNullValue_thenThrowsNPE() {
        concurrentMap.replace("test", null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceNullKey_thenThrowsNPE() {
        concurrentMap.replace(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenReplaceAllMappingNull_thenThrowsNPE() {
        concurrentMap.put("test", new Object());
        concurrentMap.replaceAll((s, o) -> null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenRemoveNullKey_thenThrowsNPE() {
        concurrentMap.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenRemoveNullKeyWithValue_thenThrowsNPE() {
        concurrentMap.remove(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenMergeNullKeyWithValue_thenThrowsNPE() {
        concurrentMap.merge(null, new Object(), (o, o2) -> o2);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenMergeKeyWithNullValue_thenThrowsNPE() {
        concurrentMap.put("test", new Object());
        concurrentMap.merge("test", null, (o, o2) -> o2);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMapAndAssumeKeyAbsent_whenComputeWithNullKey_thenThrowsNPE() {
        concurrentMap.computeIfAbsent(null, s -> s);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMapAndAssumeKeyPresent_whenComputeWithNullKey_thenThrowsNPE() {
        concurrentMap.computeIfPresent(null, (s, o) -> o);
    }

    @Test(expected = NullPointerException.class)
    public void givenConcurrentHashMap_whenComputeWithNullKey_thenThrowsNPE() {
        concurrentMap.compute(null, (s, o) -> o);
    }

    @Test
    public void givenConcurrentHashMap_whenMergeKeyRemappingNull_thenRemovesMapping() {
        Object oldValue = new Object();
        concurrentMap.put("test", oldValue);
        concurrentMap.merge("test", new Object(), (o, o2) -> null);
        assertNull(concurrentMap.get("test"));
    }

    @Test
    public void givenConcurrentHashMapAndKeyAbsent_whenComputeWithKeyRemappingNull_thenRemainsAbsent() {
        concurrentMap.computeIfPresent("test", (s, o) -> null);
        assertNull(concurrentMap.get("test"));
    }

    @Test
    public void givenKeyPresent_whenComputeIfPresentRemappingNull_thenMappingRemoved() {
        Object oldValue = new Object();
        concurrentMap.put("test", oldValue);
        concurrentMap.computeIfPresent("test", (s, o) -> null);
        assertNull(concurrentMap.get("test"));
    }

    @Test
    public void givenKeyPresent_whenComputeRemappingNull_thenMappingRemoved() {
        Object oldValue = new Object();
        concurrentMap.put("test", oldValue);
        concurrentMap.compute("test", (s, o) -> null);
        assertNull(concurrentMap.get("test"));
    }

}
