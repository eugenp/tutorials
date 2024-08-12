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

public class UpdatingJsonArrayValuesUnitTest {

    @Test
    public void givenJSONArray_whenUsingOrgJson_thenArrayCreatedAndVerified() {
        JSONArray jsonArray = new JSONArray().put("Apple").put("Banana").put("Cherry");

        assertEquals("[\"Apple\",\"Banana\",\"Cherry\"]", jsonArray.toString());
    }

    @Test
    public void givenJSONArray_whenUsingOrgJson_thenArrayReadAndUpdated() {
        JSONArray jsonArray = new JSONArray("[\"Apple\",\"Banana\",\"Cherry\"]");

        jsonArray.put(1, "Blueberry");
        assertEquals("[\"Apple\",\"Blueberry\",\"Cherry\"]", jsonArray.toString());
    }

    @Test
    public void givenJsonArray_whenUsingGson_thenArrayCreatedAndVerified() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive("Apple"));
        jsonArray.add(new JsonPrimitive("Banana"));
        jsonArray.add(new JsonPrimitive("Cherry"));

        assertEquals("[\"Apple\",\"Banana\",\"Cherry\"]", jsonArray.toString());
    }

    @Test
    public void givenJsonArray_whenUsingGson_thenArrayReadAndUpdated() {
        JsonArray jsonArray = JsonParser.parseString("[\"Apple\",\"Banana\",\"Cherry\"]").getAsJsonArray();

        jsonArray.set(1, new JsonPrimitive("Blueberry"));
        assertEquals("[\"Apple\",\"Blueberry\",\"Cherry\"]", jsonArray.toString());
    }

    @Test
    public void givenArrayNode_whenUsingJackson_thenArrayCreatedAndVerified() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode().add("Apple").add("Banana").add("Cherry");

        assertEquals("[\"Apple\",\"Banana\",\"Cherry\"]", arrayNode.toString());
    }

     @Test
    public void givenArrayNode_whenUsingJackson_thenArrayReadAndUpdated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = (ArrayNode) mapper.readTree("[\"Apple\",\"Banana\",\"Cherry\"]");

        arrayNode.set(1, "Blueberry");
        assertEquals("[\"Apple\",\"Blueberry\",\"Cherry\"]", arrayNode.toString());
    }
}
