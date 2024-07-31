package com.baeldung.jackson.bidirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomListSerializer extends StdSerializer<List<ItemWithSerializer>> {

    private static final long serialVersionUID = 3698763098000900856L;

    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(final Class<List<ItemWithSerializer>> t) {
        super(t);
    }

    @Override
    public void serialize(final List<ItemWithSerializer> items, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
        final List<Integer> ids = new ArrayList<Integer>();
        for (final ItemWithSerializer item : items) {
            ids.add(item.id);
        }
        generator.writeObject(ids);
    }
}
