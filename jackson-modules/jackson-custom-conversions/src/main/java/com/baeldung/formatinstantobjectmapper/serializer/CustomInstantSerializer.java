package com.baeldung.formatinstantobjectmapper.serializer;

import java.io.IOException;
import java.time.Instant;

import com.baeldung.formatinstantobjectmapper.utils.Instants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomInstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant instant, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeString(Instants.FORMATTER.format(instant));
    }
}
