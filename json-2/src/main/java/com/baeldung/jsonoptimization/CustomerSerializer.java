package com.baeldung.jsonoptimization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomerSerializer extends StdSerializer<Customer> {
    private static final long serialVersionUID = 1L;

    public CustomerSerializer() {
        this(null);
    }

    public CustomerSerializer(Class<Customer> t) {
        super(t);
    }

    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(customer.getId());
        jsonGenerator.writeString(customer.getFirstName());
        jsonGenerator.writeString(customer.getLastName());
        jsonGenerator.writeString(customer.getStreet());
        jsonGenerator.writeString(customer.getPostalCode());
        jsonGenerator.writeString(customer.getCity());
        jsonGenerator.writeString(customer.getState());
        jsonGenerator.writeString(customer.getPhoneNumber());
        jsonGenerator.writeString(customer.getEmail());
        jsonGenerator.writeEndArray();
    }
}
