package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToNodeTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void giveAnObject_whenConvertingIntoANode_thenCorrect() {
        MyBean fromValue = new MyBean(2016, "baeldung.com");

        JsonNode node = mapper.valueToTree(fromValue);

        assertEquals(2016, node.get("id").intValue());
        assertEquals("baeldung.com", node.get("name").textValue());
    }
}
