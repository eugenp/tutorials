package com.baeldung.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getText();
        if (value != null && value.equals("+")) {
            return Boolean.TRUE;
        } else if (value != null && value.equals("-")) {
            return Boolean.FALSE;
        } else {
            throw new IllegalArgumentException("Only values accepted as Boolean are + and -");
        }
    }
}