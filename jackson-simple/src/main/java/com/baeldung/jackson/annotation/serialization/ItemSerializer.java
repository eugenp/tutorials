package com.baeldung.jackson.annotation.serialization;

import com.baeldung.jackson.annotation.dtos.Item;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class ItemSerializer extends StdSerializer<Item> {

    private static final long serialVersionUID = 6739170890621978901L;

    public ItemSerializer() {
        super(Item.class);
    }

    public ItemSerializer(final Class<Item> t) {
        super(t);
    }

    @Override
    public final void serialize(final Item value, final JsonGenerator jgen, final SerializationContext provider) throws JacksonException {
        jgen.writeStartObject();
        jgen.writeNumberProperty("id", value.id);
        jgen.writeStringProperty("itemName", value.itemName);
        jgen.writeNumberProperty("owner", value.owner.id);
        jgen.writeEndObject();
    }

}