package com.baeldung.jsondeserialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class JsonDeserializerServiceUnitTest {
    private JsonDeserializerService deserializerService;
    private String sampleJson;

    @BeforeEach
    void setUp() {
        deserializerService = new JsonDeserializerService();
        sampleJson = """
            {
                "name": "John",
                "age": 30,
                "isActive": true,
                "salary": 50000.75,
                "hobbies": ["reading", "coding"],
                "address": {
                    "street": "123 Main St",
                    "city": "New York"
                }
            }
            """;
    }

    @Test
    void givenJsonString_whenUsingJackson_thenReturnCorrectDataTypes() {
        Map<String, Object> result = deserializerService.deserializeUsingJackson(sampleJson);

        assertEquals("John", result.get("name"));
        assertEquals(30, result.get("age"));
        assertEquals(true, result.get("isActive"));
        assertEquals(50000.75, (Double) result.get("salary"), 0.0001);
        assertTrue(result.get("hobbies") instanceof ArrayList);
        assertTrue(result.get("address") instanceof Map);

        List<String> hobbies = (ArrayList<String>) result.get("hobbies");
        assertEquals("reading", hobbies.get(0));
        assertEquals("coding", hobbies.get(1));

        Map<String, Object> address = (Map<String, Object>) result.get("address");
        assertEquals("123 Main St", address.get("street"));
        assertEquals("New York", address.get("city"));
    }

    @Test
    void givenJsonString_whenUsingGson_thenReturnCorrectDataTypes() {
        Map<String, Object> result = deserializerService.deserializeUsingGson(sampleJson);

        assertEquals("John", result.get("name"));
        assertEquals(30.0, result.get("age"));
        assertEquals(true, result.get("isActive"));
        assertEquals(50000.75, result.get("salary"));
        assertTrue(result.get("hobbies") instanceof ArrayList);
        assertTrue(result.get("address") instanceof Map);

        List<String> hobbies = (ArrayList<String>) result.get("hobbies");
        assertEquals("reading", hobbies.get(0));
        assertEquals("coding", hobbies.get(1));

        Map<String, Object> address = (Map<String, Object>) result.get("address");
        assertEquals("123 Main St", address.get("street"));
        assertEquals("New York", address.get("city"));
    }

    @Test
    void givenJsonString_whenUsingOrgJson_thenReturnCorrectDataTypes() {
        Map<String, Object> result = deserializerService.deserializeUsingOrgJson(sampleJson);

        assertEquals("John", result.get("name"));
        assertEquals(30, result.get("age"));
        assertEquals(true, result.get("isActive"));
        assertEquals(50000.75, ((Number) result.get("salary")).doubleValue());
        assertTrue(result.get("hobbies") instanceof List);
        assertTrue(result.get("address") instanceof Map);

        List<Object> hobbies = (List<Object>) result.get("hobbies");
        assertEquals("reading", hobbies.get(0));
        assertEquals("coding", hobbies.get(1));

        Map<String, Object> address = (Map<String, Object>) result.get("address");
        assertEquals("123 Main St", address.get("street"));
        assertEquals("New York", address.get("city"));
    }

    @Test
    void givenJsonString_whenUsingJsonP_thenReturnCorrectDataTypes() {
        Map<String, Object> result = deserializerService.deserializeUsingJsonP(sampleJson);

        assertEquals("John", result.get("name"));
        assertEquals(30.0, result.get("age"));
        assertEquals(true, result.get("isActive"));
        assertEquals(50000.75, result.get("salary"));
        assertTrue(result.get("hobbies") instanceof List);
        assertTrue(result.get("address") instanceof Map);

        List<Object> hobbies = (List<Object>) result.get("hobbies");
        assertEquals("reading", hobbies.get(0));
        assertEquals("coding", hobbies.get(1));

        Map<String, Object> address = (Map<String, Object>) result.get("address");
        assertEquals("123 Main St", address.get("street"));
        assertEquals("New York", address.get("city"));
    }

    @Test
    void givenEmptyJsonObject_whenUsingJackson_thenReturnEmptyMap() {
        String emptyJson = "{}";
        Map<String, Object> result = deserializerService.deserializeUsingJackson(emptyJson);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenJsonWithDifferentNumberTypes_whenUsingDifferentLibraries_thenVerifyTypeHandling() {
        String numberJson = """
            {
                "integerValue": 42,
                "doubleValue": 3.14,
                "longValue": 9223372036854775807
            }
            """;

        Map<String, Object> jacksonResult = deserializerService.deserializeUsingJackson(numberJson);
        Map<String, Object> gsonResult = deserializerService.deserializeUsingGson(numberJson);
        Map<String, Object> jsonPResult = deserializerService.deserializeUsingJsonP(numberJson);
        Map<String, Object> orgJsonResult = deserializerService.deserializeUsingOrgJson(numberJson);

        assertTrue(jacksonResult.get("integerValue") instanceof Integer);
        assertTrue(jacksonResult.get("doubleValue") instanceof Double);

        assertTrue(gsonResult.get("integerValue") instanceof Double);
        assertTrue(gsonResult.get("doubleValue") instanceof Double);

        assertTrue(jsonPResult.get("integerValue") instanceof Double);
        assertTrue(jsonPResult.get("doubleValue") instanceof Double);

        assertTrue(orgJsonResult.get("integerValue") instanceof Integer);
        assertTrue(orgJsonResult.get("doubleValue") instanceof BigDecimal);

    }
}
