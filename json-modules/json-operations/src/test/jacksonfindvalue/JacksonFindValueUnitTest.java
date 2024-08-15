package com.baeldung.jacksonfindvalue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonFindValueUnitTest {

    @Test
    public void givenJson_whenUsingFindValue_thenValueRetrieved() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"email\": \"john.doe@example.com\", \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String email = rootNode.findValue("email").asText();

        assertEquals("john.doe@example.com", email);
    }

    @Test
    public void givenJsonWithMissingKey_whenUsingFindValue_thenDefaultValue() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        JsonNode emailNode = rootNode.findValue("email");
        String email = (emailNode != null) ? emailNode.asText() : "not provided";

        assertEquals("not provided", email);
    }

    @Test
    public void givenJsonArray_whenUsingFindValues_thenAllValuesRetrieved() throws Exception {
        String jsonString = "{ \"users\": [ { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"email\": \"john.doe@example.com\" } }, { \"id\": 2, \"name\": \"Jane Doe\", \"contact\": { \"email\": \"jane.doe@example.com\" } } ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        List<String> emails = new ArrayList<>();
        rootNode.findValues("email").forEach(emailNode -> emails.add(emailNode.asText()));

        assertEquals(Arrays.asList("john.doe@example.com", "jane.doe@example.com"), emails);
    }

    @Test
    public void givenDeeplyNestedJson_whenUsingAtMethod_thenValueRetrieved() throws Exception {
        String jsonString = "{ \"organization\": { \"department\": { \"team\": { \"lead\": { \"name\": \"Alice\", \"contact\": { \"email\": \"alice@example.com\" } } } } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String email = rootNode.at("/organization/department/team/lead/contact/email").asText();

        assertEquals("alice@example.com", email);
    }

    @Test
    public void givenJson_whenUsingOptional_thenValueOrDefault() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        Optional<JsonNode> emailNode = Optional.ofNullable(rootNode.findValue("email"));
        String email = emailNode.map(JsonNode::asText).orElse("not provided");

        assertEquals("not provided", email);
    }

    @Test
    public void givenNestedJson_whenUsingCustomMethod_thenValueFound() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"email\": \"john.doe@example.com\", \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String email = findNestedValue(rootNode, "email");

        assertEquals("john.doe@example.com", email);
    }

    public String findNestedValue(JsonNode node, String key) {
        if (node.has(key)) {
            return node.get(key).asText();
        }
        for (JsonNode child : node) {
            String value = findNestedValue(child, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}