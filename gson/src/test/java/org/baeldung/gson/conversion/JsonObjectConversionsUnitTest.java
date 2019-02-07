package org.baeldung.gson.conversion;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JsonObjectConversionsUnitTest {

    @Test
    void givenAStringAndJsonParser_shouldConvertToJsonObject() throws Exception {
        // Example 1: Using JsonParser
        String json = "{ \"name\": \"Baeldung\", \"java\": true }";
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Assert.assertTrue(jsonObject.isJsonObject());
        Assert.assertTrue(jsonObject.get("name").getAsString().equals("Baeldung"));
        Assert.assertTrue(jsonObject.get("java").getAsBoolean() == true);
    }

    @Test
    void givenAStringAndGsonInstance_shouldConvertToJsonObject() throws Exception {
        // Example 2: Using fromJson
        String json = "{ \"name\": \"Baeldung\", \"java\": true }";
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        Assert.assertTrue(convertedObject.isJsonObject());
    }

}