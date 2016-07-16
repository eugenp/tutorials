package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJsonNode extends Example
{
    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonToJsonNode() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    @Test
    public void testExample() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(EXAMPLE_JSON);
        assertNotNull(jsonNode);
        assertThat(jsonNode.get("color").asText(), containsString("Black"));
    }
}
