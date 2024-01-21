package com.baeldung.custom.deserialization.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

public class CustomDeserializer extends LocalDateTimeDeserializer {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        try {
            return OffsetDateTime.parse(jsonParser.getText()).atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } catch (Exception e) {
            return super.deserialize(jsonParser, ctxt);
        }
    }
}
