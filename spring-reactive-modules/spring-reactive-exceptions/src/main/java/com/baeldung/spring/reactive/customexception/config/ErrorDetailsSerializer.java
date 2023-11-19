package com.baeldung.spring.reactive.customexception.config;

import java.io.IOException;

import com.baeldung.spring.reactive.customexception.model.ErrorDetails;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ErrorDetailsSerializer extends JsonSerializer<ErrorDetails> {
    @Override
    public void serialize(ErrorDetails value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("code", value.getErrorCode()
          .toString());
        gen.writeStringField("message", value.getErrorMessage());
        gen.writeStringField("reference", value.getReferenceUrl());
        gen.writeEndObject();
    }
}

