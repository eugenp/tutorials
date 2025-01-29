package com.baeldung.jsonobjectkeyextraction;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JSONObjectKeySetUnitTest {

    @Test
    void givenJSONObject_whenUsingKeySet_thenKeysExtracted() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("age", 30);
        jsonObject.put("isEmployed", true);

        Set<String> keys = jsonObject.keySet();

        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("age"));
        assertTrue(keys.contains("isEmployed"));
        assertEquals(3, keys.size());
    }

    @Test
    void givenJSONObject_whenIteratingKeys_thenValuesProcessed() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("age", 30);
        jsonObject.put("isEmployed", true);

        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            Object value = jsonObject.get(key);
            assertNotNull(value);
        }
    }

    @Test
    void givenNestedJSONObject_whenUsingKeySet_thenAllKeysExtracted() {
        JSONObject address = new JSONObject();
        address.put("city", "New York");
        address.put("zipCode", "10001");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("address", address);

        Set<String> keys = jsonObject.keySet();

        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("address"));
        assertEquals(2, keys.size());

        JSONObject nestedAddress = jsonObject.getJSONObject("address");
        Set<String> nestedKeys = nestedAddress.keySet();

        assertTrue(nestedKeys.contains("city"));
        assertTrue(nestedKeys.contains("zipCode"));
        assertEquals(2, nestedKeys.size());
    }
}