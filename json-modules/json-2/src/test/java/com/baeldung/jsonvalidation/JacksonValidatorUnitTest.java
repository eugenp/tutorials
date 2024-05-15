package com.baeldung.jsonvalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JacksonValidatorUnitTest {

    private final JacksonValidator validator = new JacksonValidator();

    @Test
    public void givenValidObjectJson_whenValidating_thenValid() {
        String json = "{\"email\": \"example@com\", \"name\": \"John\"}";
        assertTrue(validator.isValid(json));
    }

    @Test
    public void givenValidArrayJson_whenValidating_thenValid() {
        String json = "[{\"email\": \"example@com\", \"name\": \"John\"},{\"email\": \"example1@com\", \"name\": \"Bob\"}]";
        assertTrue(validator.isValid(json));
    }

    @Test
    public void givenInvalidJson_whenValidating_thenInvalid() {
        String json = "Invalid_Json";
        assertFalse(validator.isValid(json));
    }
}
