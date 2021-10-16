package com.baeldung.jackson.snakecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnakeCaseUnitTest {

    private static final String JSON = "{\"first_name\": \"Jackie\", \"last_name\": \"Chan\"}";

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(JSON, User.class);
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void whenExceptionThrown_thenExpectationSatisfied() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(JSON, User.class);
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithJsonPropertyAnnotation_thenGetExpectedObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User1 user = objectMapper.readValue(JSON, User1.class);
        assertEquals(user.getFirstName(), "Jackie");
        assertEquals(user.getLastName(), "Chan");
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithJsonNamingAnnotation_thenGetExpectedObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User2 user = objectMapper.readValue(JSON, User2.class);
        assertEquals(user.getFirstName(), "Jackie");
        assertEquals(user.getLastName(), "Chan");
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithCustomMapper_thenGetExpectedObject() throws JsonProcessingException {
        ObjectMapper objectMapper = getCustomObjectMapper();
        User user = objectMapper.readValue(JSON, User.class);
        assertEquals(user.getFirstName(), "Jackie");
        assertEquals(user.getLastName(), "Chan");
    }

    private ObjectMapper getCustomObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }
}
