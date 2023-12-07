package com.baeldung.gson.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JsonObjectConversionsUnitTest {

    @Test
    void whenUsingJsonParser_thenConvertToJsonObject() throws Exception {
        // Example 1: Using JsonParser
        String json = "{ \"name\": \"Baeldung\", \"java\": true }";

        JsonObject jsonObject = JsonParser.parseString(json)
            .getAsJsonObject();

        assertTrue(jsonObject.isJsonObject());
        assertEquals("Baeldung", jsonObject.get("name")
            .getAsString());
        assertTrue(jsonObject.get("java")
            .getAsBoolean());
    }

    @Test
    void whenUsingGsonInstanceFromJson_thenConvertToJsonObject() throws Exception {
        // Example 2: Using fromJson
        String json = "{ \"name\": \"Baeldung\", \"java\": true }";

        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

        assertTrue(convertedObject.isJsonObject());
        assertEquals("Baeldung", convertedObject.get("name")
            .getAsString());
        assertTrue(convertedObject.get("java")
            .getAsBoolean());
    }

}
