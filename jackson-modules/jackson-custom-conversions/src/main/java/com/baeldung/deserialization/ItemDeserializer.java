package com.baeldung.deserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class ItemDeserializer extends StdDeserializer<Item> {

    private static final long serialVersionUID = 1883547683050039861L;

    public ItemDeserializer() {
        this(null);
    }

    public ItemDeserializer(final Class<?> vc) {
        super(vc);
    }

    /**
     * {"id":1,"itemNr":"theItem","owner":2}
     */
    @Override
    public Item deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode node = jp.getCodec()
            .readTree(jp);
        final int id = (Integer) ((IntNode) node.get("id")).numberValue();
        final String itemName = node.get("itemName")
            .asText();
        final int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();

        return new Item(id, itemName, new User(userId, null));
    }

}