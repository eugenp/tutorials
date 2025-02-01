package com.baeldung.jacksonfindvalue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JacksonFindValueUnitTest {

    @Test
    void givenJson_whenUsingFindValue_thenValueRetrieved() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"email\": \"john.doe@example.com\", \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String email = rootNode.findValue("email").asText();

        assertEquals("john.doe@example.com", email);
    }

    @Test
    void givenJsonWithMissingKey_whenUsingFindValue_thenReturnNull() throws Exception {
        String jsonString = "{ \"user\": { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"phone\": \"123-456-7890\" } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        JsonNode emailNode = rootNode.findValue("email");

        assertNull(emailNode);
    }

    @Test
    void givenJsonArray_whenUsingFindValues_thenAllValuesRetrieved() throws Exception {
        String jsonString = "{ \"users\": [ { \"id\": 1, \"name\": \"John Doe\", \"contact\": { \"email\": \"john.doe@example.com\" } }, { \"id\": 2, \"name\": \"Jane Doe\", \"contact\": { \"email\": \"jane.doe@example.com\" } } ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        List<String> emails = rootNode.findValues("email")
            .stream()
            .map(JsonNode::asText)
            .collect(Collectors.toList());

        assertThat(emails)
            .containsExactly("john.doe@example.com", "jane.doe@example.com");
    }

    @Test
    void givenDeeplyNestedJson_whenUsingAtMethod_thenValueRetrieved() throws Exception {
        String jsonString = "{ \"organization\": { \"department\": { \"team\": { \"lead\": { \"name\": \"Alice\", \"contact\": { \"email\": \"alice@example.com\" } } } } } }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String email = rootNode.at("/organization/department/team/lead/contact/email").asText();

        assertEquals("alice@example.com", email);
    }
}
