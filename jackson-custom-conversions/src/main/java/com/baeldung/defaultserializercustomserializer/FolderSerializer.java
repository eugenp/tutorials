package com.baeldung.defaultserializercustomserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FolderSerializer extends StdSerializer<Folder> {

    public FolderSerializer() {
        super(Folder.class);
    }

    @Override
    public void serialize(Folder value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());

        gen.writeArrayFieldStart("files");
        for (File file : value.getFiles()) {
            gen.writeStartObject();
            gen.writeNumberField("id", file.getId());
            gen.writeStringField("name", file.getName());
            gen.writeEndObject();
        }
        gen.writeEndArray();

        gen.writeEndObject();

    }

}
