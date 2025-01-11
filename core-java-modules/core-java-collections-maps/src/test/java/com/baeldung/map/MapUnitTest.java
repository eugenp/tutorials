package com.baeldung.map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.*;

public class MapUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(MapUnitTest.class);

    
    @Test
    public void givenHashMap_whenRetrievesKeyset_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Set<String> keys = map.keySet();

        assertEquals(2, keys.size());
        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("type"));
    }

    @Test
    public void givenHashMap_whenRetrievesValues_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Collection<String> values = map.values();

        assertEquals(2, values.size());
        assertTrue(values.contains("baeldung"));
        assertTrue(values.contains("blog"));
    }

    @Test
    public void givenHashMap_whenRetrievesEntries_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Set<Entry<String, String>> entries = map.entrySet();

        assertEquals(2, entries.size());
        for (Entry<String, String> e : entries) {
            String key = e.getKey();
            String val = e.getValue();
            assertTrue(key.equals("name") || key.equals("type"));
            assertTrue(val.equals("baeldung") || val.equals("blog"));
        }
    }

    @Test
    public void givenKeySet_whenChangeReflectsInMap_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        assertEquals(2, map.size());

        Set<String> keys = map.keySet();

        keys.remove("name");
        assertEquals(1, map.size());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void givenIterator_whenFailsFastOnModification_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        map.remove("type");
        while (it.hasNext()) {
            String key = it.next();
        }
    }

    public void givenIterator_whenRemoveWorks_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertEquals(0, map.size());
    }

    @Test
    public void whenHashCodeIsCalledOnPut_thenCorrect() {
        MyKey key = new MyKey(1, "name");
        Map<MyKey, String> map = new HashMap<>();
        map.put(key, "val");
    }

    @Test
    public void whenHashCodeIsCalledOnGet_thenCorrect() {
        MyKey key = new MyKey(1, "name");
        Map<MyKey, String> map = new HashMap<>();
        map.put(key, "val");
        map.get(key);
    }

    @Test
    public void whenGetWorks_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "val");

        String val = map.get("key");

        assertEquals("val", val);
    }

    @Test
    public void givenNewKey_whenPutReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.put("key1", "val1");

        assertNull(rtnVal);
    }

    @Test
    public void givenUnmappedKey_whenGetReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.get("key1");

        assertNull(rtnVal);
    }

    @Test
    public void givenNullVal_whenPutReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.put("key1", null);

        assertNull(rtnVal);
    }

    @Test
    public void givenNullKeyAndVal_whenAccepts_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        map.put(null, null);
    }

    @Test
    public void givenNullVal_whenRetrieves_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key", null);

        String val = map.get("key");

        assertNull(val);
    }

    @Test
    public void whenContainsDistinguishesNullValues_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String val1 = map.get("key");
        boolean valPresent = map.containsKey("key");

        assertNull(val1);
        assertFalse(valPresent);

        map.put("key", null);
        String val = map.get("key");
        valPresent = map.containsKey("key");

        assertNull(val);
        assertTrue(valPresent);

    }

    @Test
    public void whenPutReturnsPrevValue_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        String rtnVal = map.put("key1", "val2");

        assertEquals("val1", rtnVal);
    }

    @Test
    public void whenCallsEqualsOnCollision_thenCorrect() {
        HashMap<MyKey, String> map = new HashMap<>();
        MyKey k1 = new MyKey(1, "firstKey");
        MyKey k2 = new MyKey(2, "secondKey");
        MyKey k3 = new MyKey(2, "thirdKey");

        LOG.debug("storing value for k1");
        map.put(k1, "firstValue");

        LOG.debug("storing value for k2");
        map.put(k2, "secondValue");

        LOG.debug("storing value for k3");
        map.put(k3, "thirdValue");

        LOG.debug("retrieving value for k1");
        String v1 = map.get(k1);

        LOG.debug("retrieving value for k2");
        String v2 = map.get(k2);

        LOG.debug("retrieving value for k3");
        String v3 = map.get(k3);

        assertEquals("firstValue", v1);
        assertEquals("secondValue", v2);
        assertEquals("thirdValue", v3);

    }

}
