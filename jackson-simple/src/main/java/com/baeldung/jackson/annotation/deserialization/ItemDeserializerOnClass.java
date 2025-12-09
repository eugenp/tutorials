package com.baeldung.jackson.annotation.deserialization;

import com.baeldung.jackson.annotation.dtos.ItemWithSerializer;
import com.baeldung.jackson.annotation.dtos.User;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.IntNode;

public class ItemDeserializerOnClass extends StdDeserializer<ItemWithSerializer> {

    private static final long serialVersionUID = 5579141241817332594L;

    public ItemDeserializerOnClass() {
        this(null);
    }

    public ItemDeserializerOnClass(final Class<?> vc) {
        super(vc);
    }

    /**
     * {"id":1,"itemNr":"theItem","owner":2}
     */
    @Override
    public ItemWithSerializer deserialize(final JsonParser jp, final DeserializationContext ctxt) throws JacksonException {
        final JsonNode node = jp.objectReadContext()
            .readTree(jp);
        final int id = (Integer) ((IntNode) node.get("id")).numberValue();
        final String itemName = node.get("itemName")
            .asText();
        final int userId = (Integer) ((IntNode) node.get("owner")).numberValue();

        return new ItemWithSerializer(id, itemName, new User(userId, null));
    }

}