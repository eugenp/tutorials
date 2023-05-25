package com.baeldung.jackson.jsonurlreader;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.baeldung.jackson.jsonurlreader.data.Example;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonUrlReaderIntegrationTest extends JsonMockServer {

    @Test
    public void whenStreamUrl_thenJsonStringReturned() throws Exception {
        String jsonNode = JsonUrlReader.stream(serviceUrl);

        assertEquals(jsonNode, JSON_RESPONSE);
    }

    @Test
    public void whenGetUrl_thenJsonNodeReturned() throws Exception {
        JsonNode jsonNode = JsonUrlReader.get(serviceUrl);

        assertEquals(jsonNode.get("name")
            .textValue(), "A");
        assertEquals(jsonNode.get("n")
            .intValue(), 1);
        assertEquals(jsonNode.get("real")
            .booleanValue(), true);
    }

    @Test
    public void givenType_whenGetUrl_thenTypeReturned() throws Exception {
        Example object = JsonUrlReader.get(serviceUrl, Example.class);

        Integer n = 1;
        assertEquals(object.getName(), "A");
        assertEquals(object.getN(), n);
        assertEquals(object.getReal(), true);
    }

    @Test
    public void whenGetJsonUrl_thenJsonObjectReturned() throws Exception {
        JSONObject jsonObject = JsonUrlReader.getJson(serviceUrl);

        assertEquals(jsonObject.getString("name"), "A");
        assertEquals(jsonObject.getInt("n"), 1);
        assertEquals(jsonObject.getBoolean("real"), true);
    }
}
