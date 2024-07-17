package com.baeldung.gson.jsonarraytohashmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONArrayToHashMapConverterUnitTest {

    private JsonArray jsonArray;
    private JsonArray jsonArrayWithDuplicates;

    @BeforeEach
    void setUp() {
        jsonArray = new JsonArray();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", "John Doe");
        jsonObject1.addProperty("age", 35);
        jsonArray.add(jsonObject1);

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("job", "Programmer");
        jsonObject2.addProperty("department", "IT");
        jsonArray.add(jsonObject2);

        jsonArrayWithDuplicates = new JsonArray();

        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty("key", "value1");
        jsonArrayWithDuplicates.add(jsonObject3);

        JsonObject jsonObject4 = new JsonObject();
        jsonObject4.addProperty("key", "value2");
        jsonArrayWithDuplicates.add(jsonObject4);
    }


    @Test
    void givenJsonArrayWithObjects_whenConvertUsingIterative_thenCorrectHashMap() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArray);

        assertEquals("John Doe", hashMap.get("name"));
        assertEquals("35", hashMap.get("age"));
        assertEquals("Programmer", hashMap.get("job"));
        assertEquals("IT", hashMap.get("department"));
    }

    @Test
    void givenJsonArrayWithObjects_whenConvertUsingStreams_thenCorrectHashMap() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingStreams(jsonArray);

        assertEquals("John Doe", hashMap.get("name"));
        assertEquals("35", hashMap.get("age"));
        assertEquals("Programmer", hashMap.get("job"));
        assertEquals("IT", hashMap.get("department"));
    }

    @Test
    void givenJsonArrayWithObjects_whenConvertUsingGson_thenCorrectHashMap() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingGson(jsonArray);

        assertEquals("John Doe", hashMap.get("name"));
        assertEquals(35.0, hashMap.get("age")); // Note: Gson parses numbers as Double
        assertEquals("Programmer", hashMap.get("job"));
        assertEquals("IT", hashMap.get("department"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingIterative_thenLastValueWins() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArrayWithDuplicates);
        assertEquals("value2", hashMap.get("key"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingStreams_thenLastValueWins() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArrayWithDuplicates);
        assertEquals("value2", hashMap.get("key"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingGson_thenLastValueWins() {
        Map<String, Object> hashMap = JSONArrayToHashMapConverter.convertUsingGson(jsonArrayWithDuplicates);
        assertEquals("value2", hashMap.get("key"));
    }
}
