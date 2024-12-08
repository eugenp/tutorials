package com.baeldung.gson.deprecatedjsonparser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeprecatedJsonParserUnitTest {

    String jsonString = "{\"name\": \"John\", \"age\":30, \"city\":\"New York\"}";
    JsonObject expectedJsonObject = new JsonObject();

    DeprecatedJsonParserUnitTest() {
        expectedJsonObject.addProperty("name", "John");
        expectedJsonObject.addProperty("age", 30);
        expectedJsonObject.addProperty("city", "New York");
    }

    @Test
    public void givenJsonString_whenUsingParseString_thenJsonObjectIsExpected() {
        JsonObject jsonObjectAlt = JsonParser.parseString(jsonString).getAsJsonObject();
        assertEquals(expectedJsonObject, jsonObjectAlt);
    }

    @Test
    public void givenJsonString_whenUsingParseReader_thenJsonObjectIsExpected() {
        StringReader reader = new StringReader(jsonString);
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        assertEquals(expectedJsonObject, jsonObject);
    }

    @Test
    public void givenJsonReader_whenParseUsingJsonReader_thenJsonObjectIsExpected() {
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        assertEquals(expectedJsonObject, jsonObject);
    }
}
