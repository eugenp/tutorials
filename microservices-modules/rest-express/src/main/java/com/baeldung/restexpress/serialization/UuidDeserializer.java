package com.baeldung.restexpress.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.strategicgains.repoexpress.util.UuidConverter;

import java.io.IOException;
import java.util.UUID;

public class UuidDeserializer
        extends JsonDeserializer<UUID> {
    @Override
    public UUID deserialize(JsonParser json, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return UuidConverter.parse(json.getText());
    }
}
