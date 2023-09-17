package com.baeldung.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value != null && !value.equals("TRUE") && !value.equals("FALSE")) {
            throw new IllegalArgumentException("Boolean value must come as Capital TRUE or FALSE");
        }
        return Boolean.valueOf(value);
    }
}