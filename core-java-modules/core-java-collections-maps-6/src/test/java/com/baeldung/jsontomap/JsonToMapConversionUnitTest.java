package com.baeldung.jsontomap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonToMapConversionUnitTest {
    String jsonString = "{\"id\": 123456, \"name\": \"John Doe\", \"email\": \"johndoe@example.com\", " +
            "\"age\": 30, \"address\": { \"street\": \"123 Main St\", \"city\": \"New York\", " +
            "\"country\": \"USA\" }, \"skills\": [ \"Java\", \"Python\", \"JavaScript\" ], \"isActive\": true }";

    @Test
    public void given_JsonData_whenUsingJackson_thenConvertJsonNodeToMap() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        Map<String, Object> map = objectMapper.convertValue(jsonNode, Map.class);
        Assert.assertEquals(123456, map.get("id"));
        Assert.assertEquals("John Doe", map.get("name"));
        Assert.assertEquals("johndoe@example.com", map.get("email"));
        Assert.assertEquals(30, map.get("age"));

        Map<String, Object> address = (Map<String, Object>) map.get("address");
        Assert.assertEquals("123 Main St", address.get("street"));
        Assert.assertEquals("New York", address.get("city"));
        Assert.assertEquals("USA", address.get("country"));

        List<String> skills = (List<String>) map.get("skills");
        Assert.assertEquals("Java", skills.get(0));
        Assert.assertEquals("Python", skills.get(1));
        Assert.assertEquals("JavaScript", skills.get(2));

        Assert.assertEquals(true, map.get("isActive"));
    }

    @Test
    public void given_JsonData_whenUsingGson_thenConvertJsonNodeToMap() {
        String jsonString = "{\"id\": 123456, \"name\": \"John Doe\", \"email\": \"johndoe@example.com\", " +
                "\"age\": 30, \"address\": { \"street\": \"123 Main St\", \"city\": \"New York\", " +
                "\"country\": \"USA\" }, \"skills\": [ \"Java\", \"Python\", \"JavaScript\" ], \"isActive\": true }";

        JsonElement jsonElement = JsonParser.parseString(jsonString);
        Gson gson = new Gson();

        Map<String, Object> map = gson.fromJson(jsonElement, Map.class);
        Assert.assertEquals(123456, ((Number) map.get("id")).intValue());
        Assert.assertEquals("John Doe", map.get("name"));
        Assert.assertEquals("johndoe@example.com", map.get("email"));
        Assert.assertEquals(30, ((Number) map.get("age")).intValue());

        Map<String, Object> address = (Map<String, Object>) map.get("address");
        Assert.assertEquals("123 Main St", address.get("street"));
        Assert.assertEquals("New York", address.get("city"));
        Assert.assertEquals("USA", address.get("country"));

        List<String> skills = (List<String>) map.get("skills");
        Assert.assertEquals("Java", skills.get(0));
        Assert.assertEquals("Python", skills.get(1));
        Assert.assertEquals("JavaScript", skills.get(2));

        Assert.assertEquals(true, map.get("isActive"));
    }
}
