package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EditNodeTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenANode_whenModifyingIt_thenCorrect() throws IOException {
        String newString = "{\"nick\": \"cowtowncoder\"}";
        JsonNode newNode = mapper.readTree(newString);

        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ((ObjectNode) rootNode).set("name", newNode);

        assertFalse(rootNode.path("name").path("nick").isMissingNode());
        assertEquals("cowtowncoder", rootNode.path("name").path("nick").textValue());
    }

}