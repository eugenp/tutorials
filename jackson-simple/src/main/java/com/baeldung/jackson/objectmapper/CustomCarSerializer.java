package com.baeldung.jackson.objectmapper;

import java.io.IOException;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomCarSerializer extends StdSerializer<Car> {

    private static final long serialVersionUID = 1396140685442227917L;

    public CustomCarSerializer() {
        this(null);
    }

    public CustomCarSerializer(final Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(final Car car, final JsonGenerator jsonGenerator, final SerializerProvider serializer) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("model: ", car.getType());
        jsonGenerator.writeEndObject();
    }
}
