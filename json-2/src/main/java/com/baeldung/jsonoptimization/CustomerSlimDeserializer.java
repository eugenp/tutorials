package com.baeldung.jsonoptimization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomerSlimDeserializer extends StdDeserializer<CustomerSlim> {
    private static final long serialVersionUID = 1L;

    public CustomerSlimDeserializer() {
        this(null);
    }

    public CustomerSlimDeserializer(Class<CustomerSlim> t) {
        super(t);
    }

    @Override
    public CustomerSlim deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        CustomerSlim feedback = new CustomerSlim();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        feedback.setId(node.get(0)
            .asLong());
        feedback.setName(node.get(1)
            .asText());
        feedback.setAddress(node.get(2)
            .asText());

        return feedback;
    }
}
