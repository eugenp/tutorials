package com.baeldung.jackson.objectmapper;

import java.io.IOException;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomCarSerializer extends JsonSerializer<Car>
{
    public CustomCarSerializer() { }

    @Override
    public void serialize(final Car car, final JsonGenerator jsonGenerator, final SerializerProvider serializer) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("model: ", car.getType());
        jsonGenerator.writeEndObject();
    }
}
