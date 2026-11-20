package com.baeldung.jackson.annotation.serialization;

import com.baeldung.jackson.annotation.dtos.ItemWithSerializer;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class ItemSerializerOnClass extends StdSerializer<ItemWithSerializer> {

    private static final long serialVersionUID = -1760959597313610409L;

    public ItemSerializerOnClass() {
        super(ItemWithSerializer.class);
    }

    public ItemSerializerOnClass(final Class<ItemWithSerializer> t) {
        super(t);
    }

    @Override
    public final void serialize(final ItemWithSerializer value, final JsonGenerator jgen, final SerializationContext provider) throws JacksonException {
        jgen.writeStartObject();
        jgen.writeNumberProperty("id", value.id);
        jgen.writeStringProperty("itemName", value.itemName);
        jgen.writeNumberProperty("owner", value.owner.id);
        jgen.writeEndObject();
    }

}