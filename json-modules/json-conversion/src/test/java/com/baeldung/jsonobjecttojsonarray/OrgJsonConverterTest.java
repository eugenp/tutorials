package com.baeldung.jsonobjecttojsonarray;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrgJsonConverterTest {

    @Test
    void givenFlatJSONObject_whenConvertValues_thenJSONArrayOfValues() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "Alice");

        OrgJsonConverter converter = new OrgJsonConverter();
        JSONArray result = converter.convertValuesToArray(jsonObject);

        assertEquals(2, result.length());
        assertTrue(result.toList().contains("Alice"));
    }

    @Test
    void givenFlatJSONObject_whenConvertToEntryArray_thenJSONArrayOfObjects() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("language", "Java");
        jsonObject.put("framework", "Spring");

        OrgJsonConverter converter = new OrgJsonConverter();
        JSONArray result = converter.convertToEntryArray(jsonObject);

        assertEquals(2, result.length());
        assertEquals("framework", result.getJSONObject(0).get("key"));
    }
}
