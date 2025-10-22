package com.baeldung.gson.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonKeyExtractorUnitTest {

    @Test
    void givenJson_whenTopLevelKeys_thenGetAllKeys() {
        String json = "{ \"name\":\"Henry\", \"email\":\"henry@example.com\", \"age\":25 }";
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        List<String> keys = JsonKeyExtractor.getAllKeys(jsonObject);

        assertEquals(3, keys.size());
        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("email"));
        assertTrue(keys.contains("age"));
    }

    @Test
    void givenJson_whenNestedKeys_thenGetAllKeys() {
        String json = "{ \"address\": { \"city\":\"New York\", \"zip\":\"10001\" } }";
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        List<String> keys = JsonKeyExtractor.getAllKeys(jsonObject);

        assertEquals(3, keys.size());
        assertTrue(keys.contains("address"));
        assertTrue(keys.contains("address.city"));
        assertTrue(keys.contains("address.zip"));
    }

    @Test
    void givenJson_whenEmpty_thenGetNoKeys() {
        String json = "{}";
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        List<String> keys = JsonKeyExtractor.getAllKeys(jsonObject);

        assertTrue(keys.isEmpty());
    }

    @Test
    void givenJson_whenDeeplyNestedKeys_thenGetAllKeys() {
        String json = "{ \"user\": { \"profile\": { \"contacts\": { \"email\": \"test@test.com\" } } } }";
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        List<String> keys = JsonKeyExtractor.getAllKeys(jsonObject);

        assertTrue(keys.contains("user"));
        assertTrue(keys.contains("user.profile"));
        assertTrue(keys.contains("user.profile.contacts"));
        assertTrue(keys.contains("user.profile.contacts.email"));
    }
}