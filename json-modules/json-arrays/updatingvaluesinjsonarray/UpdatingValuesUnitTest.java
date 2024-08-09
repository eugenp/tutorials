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
    public void givenJSONArray_whenUtilizingOrgJson_thenElementsAddedAndValueUpdated() {
        JSONArray jsonArray = new JSONArray().put("Apple").put("Banana").put("Cherry");

        JSONArray expectedArray = new JSONArray().put("Apple").put("Banana").put("Cherry");
        assertEquals(expectedArray.toString(), jsonArray.toString());

        jsonArray.put(1, "Blueberry");
        expectedArray.put(1, "Blueberry");
        assertEquals(expectedArray.toString(), jsonArray.toString());
    }

    @Test
    public void givenJsonArray_whenUtilizingGson_thenElementsAddedAndValueUpdated() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive("Apple"));
        jsonArray.add(new JsonPrimitive("Banana"));
        jsonArray.add(new JsonPrimitive("Cherry"));

        JsonArray expectedArray = JsonParser.parseString("[\"Apple\", \"Banana\", \"Cherry\"]").getAsJsonArray();
        assertEquals(expectedArray.toString(), jsonArray.toString());

        jsonArray.set(1, new JsonPrimitive("Blueberry"));
        expectedArray.set(1, new JsonPrimitive("Blueberry"));
        assertEquals(expectedArray.toString(), jsonArray.toString());
    }

    @Test
    public void givenArrayNode_whenUtilizingJackson_thenElementsAddedAndValueUpdated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode().add("Apple").add("Banana").add("Cherry");

        ArrayNode expectedArray = (ArrayNode) mapper.readTree("[\"Apple\", \"Banana\", \"Cherry\"]");
        assertEquals(expectedArray.toString(), arrayNode.toString());

        arrayNode.set(1, new TextNode("Blueberry"));
        expectedArray.set(1, new TextNode("Blueberry"));
        assertEquals(expectedArray.toString(), arrayNode.toString());
    }

}
