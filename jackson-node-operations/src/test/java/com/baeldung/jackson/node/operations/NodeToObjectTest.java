package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NodeToObjectTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenANode_whenConvertingIntoAnObject_thenCorrect() throws JsonProcessingException {
        JsonNode node = mapper.createObjectNode();
        ((ObjectNode) node).put("id", 2016);
        ((ObjectNode) node).put("name", "baeldung.com");

        MyBean toValue = mapper.treeToValue(node, MyBean.class);

        assertEquals(2016, toValue.getId());
        assertEquals("baeldung.com", toValue.getName());
    }
}
