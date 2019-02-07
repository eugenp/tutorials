package org.baeldung.jackson.dtos.withEnum;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TypeSerializer extends JsonSerializer<TypeEnumWithCustomSerializer> {

    @Override
    public void serialize(final TypeEnumWithCustomSerializer value, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(value.getId());
        generator.writeFieldName("name");
        generator.writeString(value.getName());
        generator.writeEndObject();
    }

}