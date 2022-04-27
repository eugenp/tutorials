package com.baeldung.jsonvalidation;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonValidator {

    public boolean isValid(String json) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readTree(json);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }
}
