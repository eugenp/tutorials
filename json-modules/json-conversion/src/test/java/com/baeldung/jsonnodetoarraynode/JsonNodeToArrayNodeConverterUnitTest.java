package com.baeldung.jsonnodetoarraynode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonNodeToArrayNodeConverterUnitTest {
    @Test
    void givenJsonNode_whenUsingJsonNodeMethods_thenConvertToArrayNode() throws JsonProcessingException {
        int count = 0;
        String json = "{\"objects\": [\"One\", \"Two\", \"Three\"]}";
        final JsonNode arrayNode = new ObjectMapper().readTree(json).get("objects");
        assertNotNull(arrayNode, "The 'objects' array should not be null");
        assertTrue(arrayNode.isArray(), "The 'objects' should be an array");
        if (arrayNode.isArray()) {
            for (final JsonNode objNode : arrayNode) {
                assertNotNull(objNode, "Array element should not be null");
                count++;
            }
        }
        assertEquals(3, count, "The 'objects' array should have 3 elements");
    }

     @Test
     void givenJsonNode_whenUsingCreateArrayNode_thenConvertToArrayNode() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode originalJsonNode = objectMapper.readTree("{\"objects\": [\"One\", \"Two\", \"Three\"]}");
        ArrayNode arrayNode = objectMapper.createArrayNode();
        originalJsonNode.get("objects").elements().forEachRemaining(arrayNode::add);
        assertEquals("[\"One\",\"Two\",\"Three\"]", arrayNode.toString());
    }

    @Test
     void givenJsonNode_whenUsingStreamSupport_thenConvertToArrayNode() throws Exception {
        String json = "{\"objects\": [\"One\", \"Two\", \"Three\"]}";
        JsonNode obj = new ObjectMapper().readTree(json);
        List<JsonNode> objects = StreamSupport
            .stream(obj.get("objects").spliterator(), false)
            .collect(Collectors.toList());

        assertEquals(3, objects.size(), "The 'objects' list should contain 3 elements");

        JsonNode firstObject = objects.get(0);
        assertEquals("One", firstObject.asText(), "The first element should be One");
    }

    @Test
     void givenJsonNode_whenUsingIterator_thenConvertToArrayNode() throws Exception {
        String json = "{\"objects\": [\"One\", \"Two\", \"Three\"]}";
        JsonNode datasets = new ObjectMapper().readTree(json);
        Iterator<JsonNode> iterator = datasets.withArray("objects").elements();

        int count = 0;
        while (iterator.hasNext()) {
            JsonNode dataset = iterator.next();
            System.out.print(dataset.toString() + " ");
            count++;
        }
        assertEquals(3, count, "The 'objects' list should contain 3 elements");
    }
}
