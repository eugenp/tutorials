package com.baeldung.jackson.objectmapper;

import java.io.IOException;

import com.baeldung.jackson.objectmapper.dto.Car;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class CustomCarSerializer extends StdSerializer<Car> {

    private static final long serialVersionUID = 1396140685442227917L;

    public CustomCarSerializer() {
        this(null);
    }

    public CustomCarSerializer(final Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(final Car car, final JsonGenerator jsonGenerator, final SerializationContext serializer) throws JacksonException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringProperty("model: ", car.getType());
        jsonGenerator.writeEndObject();
    }
}
