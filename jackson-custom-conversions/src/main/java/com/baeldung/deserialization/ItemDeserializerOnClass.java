package com.baeldung.deserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class ItemDeserializerOnClass extends StdDeserializer<ItemWithDeserializer> {

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
    public ItemWithDeserializer deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode node = jp.getCodec()
            .readTree(jp);
        final int id = (Integer) ((IntNode) node.get("id")).numberValue();
        final String itemName = node.get("itemName")
            .asText();
        final int userId = (Integer) ((IntNode) node.get("owner")).numberValue();

        return new ItemWithDeserializer(id, itemName, new User(userId, null));
    }

}