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
        jsonObject2.addProperty("name", "Mary Jenn");
        jsonObject2.addProperty("age", 41);
        jsonArray.add(jsonObject2);

        jsonArrayWithDuplicates = new JsonArray();

        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty("name", "John Doe");
        jsonObject3.addProperty("age", 35);
        jsonArrayWithDuplicates.add(jsonObject3);

        JsonObject jsonObject4 = new JsonObject();
        jsonObject4.addProperty("name", "John Doe");
        jsonObject4.addProperty("age", 41);
        jsonArrayWithDuplicates.add(jsonObject4);
    }


    @Test
    void givenJsonArrayWithObjects_whenConvertUsingIterative_thenCorrectHashMap() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArray);

        assertEquals(35, hashMap.get("John Doe"));
        assertEquals(41, hashMap.get("Mary Jenn"));
    }

    @Test
    void givenJsonArrayWithObjects_whenConvertUsingStreams_thenCorrectHashMap() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingStreams(jsonArray);

        assertEquals(35, hashMap.get("John Doe"));
        assertEquals(41, hashMap.get("Mary Jenn"));
    }

    @Test
    void givenJsonArrayWithObjects_whenConvertUsingGson_thenCorrectHashMap() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingGson(jsonArray);

        assertEquals(35, hashMap.get("John Doe"));
        assertEquals(41, hashMap.get("Mary Jenn"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingIterative_thenLastValueWins() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArrayWithDuplicates);
        assertEquals(41, hashMap.get("John Doe"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingStreams_thenLastValueWins() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingIterative(jsonArrayWithDuplicates);
        assertEquals(41, hashMap.get("John Doe"));
    }

    @Test
    void givenJsonArrayWithDuplicateKeys_whenConvertUsingGson_thenLastValueWins() {
        Map<String, Integer> hashMap = JSONArrayToHashMapConverter.convertUsingGson(jsonArrayWithDuplicates);
        assertEquals(41, hashMap.get("John Doe"));
    }
}
