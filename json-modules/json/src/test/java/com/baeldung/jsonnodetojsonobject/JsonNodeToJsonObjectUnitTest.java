package com.baeldung.jsonnodetojsonobject;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonNodeToJsonObjectUnitTest {
    public static String jsonString = "{\"name\": \"John\", \"gender\": \"male\", \"company\": \"Baeldung\", \"email\": \"john@baeldung.com\", \"address\": \"New Mexico\"}";

    @Test
    public void givenJsonNode_whenConvertingToObjectNode_thenVerifyFieldsIntegrity() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectNode objectNode = objectMapper.createObjectNode().setAll((ObjectNode) jsonNode);
        assertEquals("John", objectNode.get("name").asText());
        assertEquals("male", objectNode.get("gender").asText());
        assertEquals("Baeldung", objectNode.get("company").asText());
    }
}
