package com.baeldung.jackson.blob;

import java.io.IOException;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

@JacksonStdImpl
public class SqlBlobDeserializer extends JsonDeserializer<Blob> {
    @Override
    public Blob deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            byte[] blobBytes = p.getBinaryValue();
            return new SerialBlob(blobBytes);
        } catch (Exception e) {
            throw new IOException("Failed to deserialize Blob", e);
        }
    }
}

