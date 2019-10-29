package com.baeldung.jackson.serialization.custom.serializer;

import java.io.IOException;

import com.baeldung.jackson.entities.Folder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FolderSerializerWithInternalObjectMapper extends StdSerializer<Folder> {

    public FolderSerializerWithInternalObjectMapper() {
        super(Folder.class);
    }

    @Override
    public void serialize(Folder value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());

        // we access internal mapper to delegate the serialization of File list  
        ObjectMapper mapper = (ObjectMapper) gen.getCodec();

        gen.writeFieldName("files");
        String stringValue = mapper.writeValueAsString(value.getFiles());
        gen.writeRawValue(stringValue);

        gen.writeEndObject();

    }

}
