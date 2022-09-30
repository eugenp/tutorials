package com.baeldung.jsonvalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GsonValidatorUnitTest {

    private final GsonValidator validator = new GsonValidator();

    @Test
    public void givenValidObjectJson_whenValidatingNonStrict_thenValid() {
        String json = "{\"email\": \"example@com\", \"name\": \"John\"}";
        assertTrue(validator.isValid(json));
    }

    @Test
    public void givenValidArrayJson_whenValidatingNonStrict_thenValid() {
        String json = "[{\"email\": \"example@com\", \"name\": \"John\"},{\"email\": \"example1@com\", \"name\": \"Bob\"}]";
        assertTrue(validator.isValid(json));
    }

    @Test
    public void givenInvalidJson_whenValidatingNonStrict_thenValid() {
        String json = "Invalid_Json";
        assertTrue(validator.isValid(json));
    }

    @Test
    public void givenInvalidJson_whenValidatingStrict_thenInvalid() {
        String json = "Invalid_Json";
        assertFalse(validator.isValidStrict(json));
    }
}
