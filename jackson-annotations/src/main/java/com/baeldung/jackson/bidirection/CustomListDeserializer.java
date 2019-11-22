package com.baeldung.jackson.bidirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomListDeserializer extends StdDeserializer<List<ItemWithSerializer>> {

    private static final long serialVersionUID = 1095767961632979804L;

    public CustomListDeserializer() {
        this(null);
    }

    public CustomListDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public List<ItemWithSerializer> deserialize(final JsonParser jsonparser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return new ArrayList<ItemWithSerializer>();
    }

}