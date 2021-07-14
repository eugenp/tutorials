package com.baeldung.jsonoptimization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomerDeserializer extends StdDeserializer<Customer> {
    private static final long serialVersionUID = 1L;

    public CustomerDeserializer() {
        this(null);
    }

    public CustomerDeserializer(Class<Customer> t) {
        super(t);
    }

    @Override
    public Customer deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        Customer feedback = new Customer();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        feedback.setId(node.get(0)
            .asLong());
        feedback.setFirstName(node.get(1)
            .asText());
        feedback.setLastName(node.get(2)
            .asText());
        feedback.setStreet(node.get(3)
            .asText());
        feedback.setPostalCode(node.get(4)
            .asText());
        feedback.setCity(node.get(5)
            .asText());
        feedback.setState(node.get(6)
            .asText());
        JsonNode phoneNumber = node.get(7);
        feedback.setPhoneNumber(phoneNumber.isNull() ? null : phoneNumber.asText());
        JsonNode email = node.get(8);
        feedback.setEmail(email.isNull() ? null : email.asText());

        return feedback;
    }
}
