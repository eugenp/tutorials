package com.baeldung.jackson.blob;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;
import java.sql.Blob;

@JacksonStdImpl
public class SqlBlobSerializer extends JsonSerializer<Blob> {
    @Override
    public void serialize(Blob value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            byte[] blobBytes = value.getBytes(1, (int) value.length());
            gen.writeBinary(blobBytes);
        } catch (Exception e) {
            throw new IOException("Failed to serialize Blob", e);
        }
    }
}
