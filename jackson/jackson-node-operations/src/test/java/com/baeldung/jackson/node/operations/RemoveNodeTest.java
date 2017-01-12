package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RemoveNodeTest {

    @Test
    public void givenANode_whenRemovingFromATree_thenCorrect() throws IOException {
        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ((ObjectNode) rootNode).remove("company");

        assertTrue(rootNode.path("company").isMissingNode());
    }

}
