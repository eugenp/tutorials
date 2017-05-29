package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AddNodeTest {
    @Test
    public void givenANode_whenAddingIntoATree_thenCorrect() throws IOException {
        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ObjectNode addedNode = ((ObjectNode) rootNode).putObject("address");
        addedNode.put("city", "Seattle").put("state", "Washington").put("country", "United States");

        assertFalse(rootNode.path("address").isMissingNode());
        assertEquals("Seattle", rootNode.path("address").path("city").textValue());
        assertEquals("Washington", rootNode.path("address").path("state").textValue());
        assertEquals("United States", rootNode.path("address").path("country").textValue());
    }
}