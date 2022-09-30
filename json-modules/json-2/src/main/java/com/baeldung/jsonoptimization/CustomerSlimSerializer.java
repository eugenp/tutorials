package com.baeldung.jsonoptimization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomerSlimSerializer extends StdSerializer<CustomerSlim> {
    private static final long serialVersionUID = 1L;

    public CustomerSlimSerializer() {
        this(null);
    }

    public CustomerSlimSerializer(Class<CustomerSlim> t) {
        super(t);
    }

    @Override
    public void serialize(CustomerSlim customer, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(customer.getId());
        jsonGenerator.writeString(customer.getName());
        jsonGenerator.writeString(customer.getAddress());
        jsonGenerator.writeEndArray();
    }
}
