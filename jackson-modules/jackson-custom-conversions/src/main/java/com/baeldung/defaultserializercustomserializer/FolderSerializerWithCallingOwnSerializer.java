package com.baeldung.defaultserializercustomserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FolderSerializerWithCallingOwnSerializer extends StdSerializer<Folder> {

    public FolderSerializerWithCallingOwnSerializer() {
        super(Folder.class);
    }

    @Override
    public void serialize(Folder value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());

        provider.defaultSerializeField("files", value.getFiles(), gen);

        provider.defaultSerializeField("details", value, gen);

        gen.writeEndObject();

    }

}
