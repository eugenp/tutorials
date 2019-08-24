package com.baeldung.jackson.serialization.custom.serializer;

import java.io.IOException;

import com.baeldung.jackson.entities.Folder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FolderSerializerWithSerializerProvider extends StdSerializer<Folder> {

    public FolderSerializerWithSerializerProvider() {
        super(Folder.class);
    }

    @Override
    public void serialize(Folder value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());

        // we delegate the File list serialization to its default serializer
        provider.defaultSerializeField("files", value.getFiles(), gen);

        gen.writeEndObject();

    }

}