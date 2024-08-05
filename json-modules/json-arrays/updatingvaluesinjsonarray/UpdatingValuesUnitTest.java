package com.baeldung.updatingvaluesinjsonarray;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatingValuesUnitTest {

    @Test
    public void givenJSONArray_whenUtilizingOrgJson_thenValueUpdated() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("Apple");
        jsonArray.put("Banana");
        jsonArray.put("Cherry");

        jsonArray.put(1, "Blueberry");

        JSONArray expectedArray = new JSONArray();
        expectedArray.put("Apple");
        expectedArray.put("Blueberry");
        expectedArray.put("Cherry");

        assertEquals(expectedArray.toString(), jsonArray.toString());
    }

    @Test
    public void givenJsonArray_whenUtilizingGson_thenValueUpdated() {
        String jsonString = "[\"Apple\", \"Banana\", \"Cherry\"]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        jsonArray.set(1, new JsonPrimitive("Blueberry"));

        JsonArray expectedArray = JsonParser.parseString("[\"Apple\", \"Blueberry\", \"Cherry\"]").getAsJsonArray();

        assertEquals(expectedArray.toString(), jsonArray.toString());
    }

    @Test
    public void givenArrayNode_whenUtilizingJackson_thenValueUpdated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "[\"Apple\", \"Banana\", \"Cherry\"]";
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(jsonString);

        arrayNode.set(1, new TextNode("Blueberry"));

        ArrayNode expectedArray = (ArrayNode) mapper.readTree("[\"Apple\", \"Blueberry\", \"Cherry\"]");

        assertEquals(expectedArray.toString(), arrayNode.toString());
    }
}