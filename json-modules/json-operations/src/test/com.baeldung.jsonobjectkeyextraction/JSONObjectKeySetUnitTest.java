package com.baeldung.jsonobjectkeyextraction;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JSONObjectKeySetUnitTest {

    private static final String jsonString = "{"
            + "\"name\": \"John Doe\", "
            + "\"age\": 30, "
            + "\"isEmployed\": true, "
            + "\"address\": {"
            + "    \"city\": \"New York\", "
            + "    \"zipCode\": \"10001\""
            + "}"
            + "}";

    @Test
    void givenJSONObject_whenUsingKeySet_thenReturnsKeys() {
        JSONObject jsonObject = new JSONObject(jsonString);
        Set<String> keys = jsonObject.keySet();
        assertEquals(Set.of("name", "age", "isEmployed", "address"), keys);
    }

    @Test
    void givenJSONString_whenParsing_thenKeysExtracted() {
        JSONObject jsonObject = new JSONObject(jsonString);
        Set<String> keys = jsonObject.keySet();

        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("age"));
        assertTrue(keys.contains("isEmployed"));
        assertTrue(keys.contains("address"));
        assertEquals(4, keys.size());
    }

    @Test
    void givenJSONObject_whenIteratingKeys_thenValuesProcessed() {
        JSONObject jsonObject = new JSONObject(jsonString);

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            assertNotNull(value);
        }
    }

    @Test
    void givenNestedJSONObject_whenUsingKeySet_thenAllKeysExtracted() {
        JSONObject jsonObject = new JSONObject(jsonString);

        Set<String> topLevelKeys = jsonObject.keySet();
        assertTrue(topLevelKeys.contains("name"));
        assertTrue(topLevelKeys.contains("address"));
        assertEquals(4, topLevelKeys.size());

        JSONObject addressObject = jsonObject.getJSONObject("address");
        Set<String> addressKeys = addressObject.keySet();
        assertTrue(addressKeys.contains("city"));
        assertTrue(addressKeys.contains("zipCode"));
        assertEquals(2, addressKeys.size());
    }
}
