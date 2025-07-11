package com.baeldung.jsongetvaluewithkeyset;

import org.json.JSONObject;

import org.junit.Test;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JSONGetValueWithKeySetUnitTest {

    @Test
    public void givenFlatJson_whenExtractKeys_thenReturnAllTopLevelKeys() {
        String json = "{\"name\":\"Jane\", \"name_id\":12345, \"city\":\"Vancouver\"}";
        Set<String> keys = JSONGetValueWithKeySet.extractKeys(json);
        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("name_id"));
        assertTrue(keys.contains("city"));
        assertEquals(3, keys.size());
    }

    @Test
    public void givenNestedJson_whenExtractNestedKeys_thenReturnAllKeysWithHierarchy() {
        String json = "{"
            + "\"user\": {"
            +     "\"id\": 101,"
            +     "\"name\": \"Gregory\""
            + "},"
            + "\"city\": {"
            +     "\"id\": 121,"
            +     "\"name\": \"Calgary\""
            + "},"
            + "\"region\": \"CA\""
        + "}";

        JSONObject jsonObject = new JSONObject(json);
        Set<String> actualKeys = new HashSet<>();
        JSONGetValueWithKeySet.extractNestedKeys(jsonObject, "", actualKeys);

        Set<String> expectedKeys = Set.of("user", "user.id", "user.name", "city",
          "city.id", "city.name", "region");
        assertEquals(expectedKeys, actualKeys);
    }
}
