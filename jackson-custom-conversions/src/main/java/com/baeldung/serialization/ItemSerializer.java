package com.baeldung.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ItemSerializer extends StdSerializer<Item> {

    private static final long serialVersionUID = 6739170890621978901L;

    public ItemSerializer() {
        this(null);
    }

    public ItemSerializer(final Class<Item> t) {
        super(t);
    }

    @Override
    public final void serialize(final Item value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.id);
        jgen.writeStringField("itemName", value.itemName);
        jgen.writeNumberField("owner", value.owner.id);
        jgen.writeEndObject();
    }

}