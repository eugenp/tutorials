package com.baeldung.jsonvalidation;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonValidator {

    final ObjectMapper mapper = new ObjectMapper();

    public boolean isValid(String json) {
        try {
            mapper.readTree(json);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }
}
