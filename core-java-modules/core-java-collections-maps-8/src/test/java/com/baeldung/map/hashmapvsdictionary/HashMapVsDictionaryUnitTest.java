package com.baeldung.map.hashmapvsdictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HashMapVsDictionaryUnitTest {

    @Test
    void given_whenUsingHashMap_thenAllowsNullKeyAndValue() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "NullKey");
        map.put("Key1", null);

        assertEquals("NullKey", map.get(null));
        assertNull(map.get("Key1"));
    }

    @Test
    void given_whenUsingHashMap_thenOverwriteExistingKey() {
        Map<String, String> map = new HashMap<>();
        map.put("Key1", "Value1");
        map.put("Key1", "Value2");

        assertEquals("Value2", map.get("Key1"));
    }

    @Test
    void given_whenUsingHashMap_thenKeyDoesNotExist() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.get("NonExistentKey"));
    }

    @Test
    void given_whenUsingHashMap_thenSynchronizedMap() {
        Map<String, String> synchronizedMap = java.util.Collections.synchronizedMap(new HashMap<>());
        synchronized (synchronizedMap) {
            synchronizedMap.put("Key1", "Value1");
            assertEquals("Value1", synchronizedMap.get("Key1"));
        }
    }

    @Test
    void given_whenUsingDictionary_thenDoesNotAllowNullKey() {
        Dictionary<String, String> dictionary = new Hashtable<>();

        assertThrows(NullPointerException.class, () -> dictionary.put(null, "NullKey"));
    }

    @Test
    void given_whenUsingDictionary_thenDoesNotAllowNullValue() {
        Dictionary<String, String> dictionary = new Hashtable<>();

        assertThrows(NullPointerException.class, () -> dictionary.put("Key1", null));
    }

    @Test
    void given_whenUsingDictionary_thenOverwriteExistingKeyDict() {
        Dictionary<String, String> dictionary = new Hashtable<>();
        dictionary.put("Key1", "Value1");
        dictionary.put("Key1", "Value2");

        assertEquals("Value2", dictionary.get("Key1"));
    }

    @Test
    void given_whenUsingDictionary_thenKeyDoesNotExistDict() {
        Dictionary<String, String> dictionary = new Hashtable<>();
        assertNull(dictionary.get("NonExistentKey"));
    }
}
