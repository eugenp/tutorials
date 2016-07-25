package com.baeldung.jackson.dtos.withEnum;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TypeSerializer extends StdSerializer<TypeEnumWithCustomSerializer> {

    private static final long serialVersionUID = -7650668914169390772L;

    public TypeSerializer() {
        this(null);
    }

    public TypeSerializer(final Class<TypeEnumWithCustomSerializer> t) {
        super(t);
    }

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