package com.baeldung.jackson.jsonurlreader;

import static org.junit.Assert.assertEquals;

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
    }

    @Test
    public void givenType_whenGetUrl_thenTypeReturned() throws Exception {
        Example object = JsonUrlReader.get(serviceUrl, Example.class);

        assertEquals(object.getName(), "A");
    }
}
