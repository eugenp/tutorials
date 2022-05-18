package com.baeldung.jsonvalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JsonValidatorUnitTest {

    private final JsonValidator validator = new JsonValidator();

    @Test
    public void givenValidObjectJson_whenValidatingObject_thenValid() {
        String json = "{\"email\": \"example@com\", \"name\": \"John\"}";
        assertTrue(validator.isValidObject(json));
    }

    @Test
    public void givenInvalidJson_whenValidating_thenInvalid() {
        String json = "Invalid_Json";
        assertFalse(validator.isValidObject(json));
    }

    @Test
    public void givenValidArrayJson_whenValidatingObject_thenInvalid() {
        String json = "[{\"email\": \"example@com\", \"name\": \"John\"},{\"email\": \"example1@com\", \"name\": \"Bob\"}]";
        assertFalse(validator.isValidObject(json));
    }

    @Test
    public void givenValidJson_whenValidatingJson_thenValid() {
        String json = "[{\"email\": \"example@com\", \"name\": \"John\"},{\"email\": \"example1@com\", \"name\": \"Bob\"}]";
        assertTrue(validator.isValidJson(json));
    }
}
