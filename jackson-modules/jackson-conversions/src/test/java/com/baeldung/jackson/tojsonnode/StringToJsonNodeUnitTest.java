package com.baeldung.jackson.tojsonnode;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringToJsonNodeUnitTest {

    @Test
    public final void whenParsingJsonStringIntoJsonNode_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualObj = mapper.readTree(jsonString);

        assertNotNull(actualObj);
    }

    @Test
    public final void givenUsingLowLevelDetails_whenParsingJsonStringIntoJsonNode_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getFactory();
        final JsonParser parser = factory.createParser(jsonString);
        final JsonNode actualObj = mapper.readTree(parser);

        assertNotNull(actualObj);
    }

    @Test
    public final void givenTheJsonNode_whenRetrievingDataFromId_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualObj = mapper.readTree(jsonString);

        // When
        final JsonNode jsonNode1 = actualObj.get("k1");
        assertThat(jsonNode1.textValue(), equalTo("v1"));
    }

}
