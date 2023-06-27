package com.baeldung.jackson.jsonnode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import java.util.Iterator;
import org.junit.Test;

public class RemoveJsonElementsUnitTest {
    @Test
    public void given_JsonData_whenUsingJackson_thenRemoveElementByKey() throws JsonProcessingException {
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectNode object = (ObjectNode) jsonNode;
        object.remove("age");
        String updatedJson = objectMapper.writeValueAsString(object);
        Assertions.assertEquals("{\"name\":\"John\",\"city\":\"New York\"}", updatedJson);
    }
    @Test
    public void given_JsonData_whenUsingJackson_thenRemoveElementsByCondition() throws JsonProcessingException {
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Iterator<JsonNode> elements = jsonNode.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            if (element.isNumber() && element.asInt() == 30) {
                elements.remove();
            }
        }
        String updatedJson = objectMapper.writeValueAsString(jsonNode);
        Assertions.assertEquals("{\"name\":\"John\",\"city\":\"New York\"}", updatedJson);
    }

    @Test
    public void given_JsonData_whenUsingJackson_thenRemoveElementFromNestedStructure() throws JsonProcessingException {
        String json = "{\"name\": \"John\", \"details\": {\"age\": 30, \"city\": \"New York\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode detailsNode = jsonNode.path("details");
        ((ObjectNode) detailsNode).remove("age");
        String updatedJson = objectMapper.writeValueAsString(jsonNode);
        Assertions.assertEquals("{\"name\":\"John\",\"details\":{\"city\":\"New York\"}}", updatedJson);
    }
}
