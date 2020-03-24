package com.baeldung.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ItemSerializerOnClass extends StdSerializer<ItemWithSerializer> {

    private static final long serialVersionUID = -1760959597313610409L;

    public ItemSerializerOnClass() {
        this(null);
    }

    public ItemSerializerOnClass(final Class<ItemWithSerializer> t) {
        super(t);
    }

    @Override
    public final void serialize(final ItemWithSerializer value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.id);
        jgen.writeStringField("itemName", value.itemName);
        jgen.writeNumberField("owner", value.owner.id);
        jgen.writeEndObject();
    }

}