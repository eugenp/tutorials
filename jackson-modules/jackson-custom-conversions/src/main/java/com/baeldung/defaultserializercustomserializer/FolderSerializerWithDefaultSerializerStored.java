package com.baeldung.defaultserializercustomserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FolderSerializerWithDefaultSerializerStored extends StdSerializer<Folder> {

    private final JsonSerializer<Object> defaultSerializer;

    public FolderSerializerWithDefaultSerializerStored(JsonSerializer<Object> defaultSerializer) {
        super(Folder.class);
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(Folder value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());

        provider.defaultSerializeField("files", value.getFiles(), gen);

        gen.writeFieldName("details");
        defaultSerializer.serialize(value, gen, provider);

        gen.writeEndObject();

    }

}
