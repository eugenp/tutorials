package com.baeldung.jackson.snakecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnakeCaseUnitTest {

    private static final String JSON = "{\"first_name\": \"Jackie\", \"last_name\": \"Chan\"}";

    @Test(expected = UnrecognizedPropertyException.class)
    public void whenExceptionThrown_thenExpectationSatisfied() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(JSON, User.class);
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithJsonPropertyAnnotation_thenGetExpectedObject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserWithPropertyNames user = objectMapper.readValue(JSON, UserWithPropertyNames.class);
        assertEquals("Jackie", user.getFirstName());
        assertEquals("Chan", user.getLastName());
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithJsonNamingAnnotation_thenGetExpectedObject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserWithSnakeStrategy user = objectMapper.readValue(JSON, UserWithSnakeStrategy.class);
        assertEquals("Jackie", user.getFirstName());
        assertEquals("Chan", user.getLastName());
    }

    @Test
    public void givenSnakeCaseJson_whenParseWithCustomMapper_thenGetExpectedObject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        User user = objectMapper.readValue(JSON, User.class);
        assertEquals("Jackie", user.getFirstName());
        assertEquals("Chan", user.getLastName());
    }

}
