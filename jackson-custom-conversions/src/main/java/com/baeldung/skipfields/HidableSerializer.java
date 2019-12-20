package com.baeldung.skipfields;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class HidableSerializer extends JsonSerializer<Hidable> {

    private JsonSerializer<Object> defaultSerializer;

    public HidableSerializer(final JsonSerializer<Object> serializer) {
        defaultSerializer = serializer;
    }

    @Override
    public void serialize(final Hidable value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        if (value.isHidden())
            return;
        defaultSerializer.serialize(value, jgen, provider);
    }

    @Override
    public boolean isEmpty(final SerializerProvider provider, final Hidable value) {
        return (value == null || value.isHidden());
    }
}
